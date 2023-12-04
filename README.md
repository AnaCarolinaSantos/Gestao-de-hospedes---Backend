# Desafio---Full-Stack-Backend
 Aplicação para gestão de hóspedes em um hotel. Permite a realização de reservas, check-in e checkout.

# BACK-END

No Spring Initializr:

```
Project: Maven
Language: Java
Spring Boot: 3.2.0
Packaging: Jar
Java: 17
Dependencies: PostgreSQL Driver, Spring Web, Spring Data JPA, Lombok 
```

No IntelliJ IDEA:

```
Compilar pelo arquivo FullStackApplication
```

OBS: Verificar URL, USER e PASSWORD no arquivo ConexaoBD

# BANCO DE DADOS

Baixar o postgres: https://www.enterprisedb.com/downloads/postgres-postgresql-downloads

OBS: Baixei a versão 15.5 para Windows

---------------

user: postgres

password: 1310

---------------

Criar o database, as tabelas e a trigger abaixo:

```
CREATE DATABASE "Hotel"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;
```

```
create table hospedes (
	id int generated always as identity primary key not null,
	nome varchar(250) not null,
	dt_nascimento date,
	cpf varchar(14) not null,
	telefone varchar(20) not null,
	email varchar(50)
);
```

```
create table reservas (
	id int generated always as identity primary key not null,
	id_hospede int not null,
	dt_inicial date not null,
	dt_final date not null,
	check_in timestamp,
	checkout timestamp,
	estacionamento boolean default false,
	qt_diarias int,
	vl_diarias numeric(6,2),
	vl_taxa_estacionamento numeric(6,2),
	vl_taxa_checkout_atrasado numeric(6,2),
	vl_total numeric(6,2),
	constraint fk_hospede foreign key (id_hospede) references hospedes(id)
);
```

```
-- FUNCTION: public.validar_reserva()

-- DROP FUNCTION IF EXISTS public.validar_reserva();

CREATE OR REPLACE FUNCTION public.validar_reserva()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
declare
	dt date;
	qt_diarias numeric;
	valor_diaria numeric := 0;
    taxa_estacionamento numeric := 0;
    taxa_checkout_atrasado numeric := 0;
begin

	-- verificar horario de check-in
	
    if new.check_in is distinct from old.check_in and extract(hour from new.check_in) < 14 then
        raise exception 'O horário para realização do check-in é a partir das 14h00min.';
    end if;
	
	
	-- calcular valores
	
	if new.checkout is distinct from old.checkout then
	
		dt := new.dt_inicial;
		qt_diarias := (extract(day from (new.dt_final - new.dt_inicial))+1);
		
		for counter in 1..qt_diarias loop
			
			-- segunda a sexta
			if extract(isodow from dt) in (1, 2, 3, 4, 5) then
				valor_diaria := valor_diaria + 120.00;
				
				-- taxa de estacionamento
				if new.estacionamento then
					taxa_estacionamento := taxa_estacionamento + 15.00;
				end if;
				
			-- sabado e domingo
			else
				valor_diaria := valor_diaria + 180.00;
				
				-- taxa de estacionamento
				if new.estacionamento then
					taxa_estacionamento := taxa_estacionamento + 20.00;
				end if;
				
			end if;
			
			dt := dt + interval '1 day';
		end loop;
		

		-- verificar horario de checkout e se necessario, calcular taxa adicional
		
		-- segunda a sexta
		if extract(hour from new.checkout) > 12 then
			if extract(isodow from new.checkout) in (1, 2, 3, 4, 5) then
				taxa_checkout_atrasado := 60.00;
			
			-- sabado e domingo
			else
				taxa_checkout_atrasado := 90.00;
				
			end if;
		end if;

		-- calculando o valor total da reserva
		
		new.qt_diarias := qt_diarias;
		new.vl_diarias := valor_diaria;
		new.vl_taxa_estacionamento := taxa_estacionamento;
		new.vl_taxa_checkout_atrasado := taxa_checkout_atrasado;
		new.vl_total := valor_diaria + taxa_estacionamento + taxa_checkout_atrasado;
		
	end if;

    return new;
end;
$BODY$;

ALTER FUNCTION public.validar_reserva()
    OWNER TO postgres;
	
CREATE TRIGGER trigger_validar_reserva
BEFORE UPDATE ON reservas
FOR EACH ROW
EXECUTE FUNCTION public.validar_reserva();
```
