CREATE TABLE public.usuario (
  id_usuario serial NOT NULL,
  nome varchar(60) NOT NULL,
  email varchar(60) NOT NULL,
  senha varchar(256) NOT NULL,
  CONSTRAINT usuario_pk PRIMARY KEY (id_usuario)
);

CREATE TABLE public.investimento (
  id_investimento serial NOT NULL,
  nome varchar(100) NOT NULL,
  usuario_id integer NOT NULL,
  CONSTRAINT investimento_pk PRIMARY KEY (id_investimento),
  CONSTRAINT investimento_fk FOREIGN KEY (usuario_id) REFERENCES public.usuario(id_usuario)
);

CREATE TABLE public.posicao (
  id_posicao serial NOT NULL,
  valor numeric(3) NOT NULL,
  data_referencia date NOT NULL DEFAULT now(),
  investimento_id integer NOT NULL,
  CONSTRAINT posicao_pk PRIMARY KEY (id_posicao),
  CONSTRAINT posicao_fk FOREIGN KEY (investimento_id) REFERENCES public.investimento(id_investimento)
);
