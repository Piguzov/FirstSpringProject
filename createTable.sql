CREATE TABLE public.expenses (
    id bigint NOT NULL,
    amount bigint NOT NULL,
    date date NOT NULL,
    description character(100),
    category character varying(20) DEFAULT 'Other'::character varying NOT NULL
);

CREATE SEQUENCE public.expenses_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE ONLY public.expenses ALTER COLUMN id SET DEFAULT nextval('public.expenses_id_seq'::regclass);

ALTER TABLE public.expenses
    ADD CONSTRAINT expenses_category_check CHECK (((category)::text = ANY ((ARRAY['Food'::character varying, 'Transport'::character varying, 'Entertainment'::character varying, 'Clothes'::character varying, 'Internet'::character varying, 'House'::character varying, 'Other'::character varying])::text[]))) NOT VALID;

ALTER TABLE ONLY public.expenses
    ADD CONSTRAINT expenses_pkey PRIMARY KEY (id);
