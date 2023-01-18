--liquibase formatted sql
--changeset psamula:1
--
-- PostgreSQL database dump
--

-- Dumped from database version 15.1
-- Dumped by pg_dump version 15.1

-- Started on 2023-01-16 23:21:14

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2 (class 3079 OID 24577)
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- TOC entry 3443 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner:
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


--
-- TOC entry 257 (class 1255 OID 33002)
--

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 215 (class 1259 OID 24588)
-- Name: authorities; Type: TABLE; Schema: public; Owner: postgres
--

--
-- TOC entry 216 (class 1259 OID 24595)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
                              id uuid NOT NULL,
                              username character varying(50) NOT NULL,
                              password character varying(100) NOT NULL,
                              enabled boolean NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

CREATE TABLE public.authorities (
                                    username character varying(50) NOT NULL,
                                    authority character varying(50) NOT NULL
);


ALTER TABLE public.authorities OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 32958)
-- Name: characterrating; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.characterrating (
                                        id bigint NOT NULL,
                                        user_id uuid,
                                        movierole_id bigint,
                                        rating integer
);


ALTER TABLE public.characterrating OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 32957)
-- Name: characterrating_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.characterrating_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.characterrating_id_seq OWNER TO postgres;

--
-- TOC entry 3444 (class 0 OID 0)
-- Dependencies: 228
-- Name: characterrating_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.characterrating_id_seq OWNED BY public.characterrating.id;


--
-- TOC entry 220 (class 1259 OID 32779)
-- Name: directorshort; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.directorshort (
                                      id integer NOT NULL,
                                      imdbid text NOT NULL,
                                      name text NOT NULL,
                                      movieid integer,
                                      staff_member_id integer
);


ALTER TABLE public.directorshort OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 32778)
-- Name: directorshort_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.directorshort_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.directorshort_id_seq OWNER TO postgres;

--
-- TOC entry 3445 (class 0 OID 0)
-- Dependencies: 219
-- Name: directorshort_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.directorshort_id_seq OWNED BY public.directorshort.id;


--
-- TOC entry 218 (class 1259 OID 32770)
-- Name: movie; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.movie (
                              id integer NOT NULL,
                              imdbid text NOT NULL,
                              title text NOT NULL,
                              fulltitle text,
                              genres text NOT NULL,
                              year integer NOT NULL,
                              plot text,
                              imdbrating numeric,
                              imdbratingvotes integer,
                              releasedate date,
                              image text
);


ALTER TABLE public.movie OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 32769)
-- Name: movie_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.movie_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.movie_id_seq OWNER TO postgres;

--
-- TOC entry 3446 (class 0 OID 0)
-- Dependencies: 217
-- Name: movie_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.movie_id_seq OWNED BY public.movie.id;


--
-- TOC entry 227 (class 1259 OID 32930)
-- Name: movierating; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.movierating (
                                    id integer NOT NULL,
                                    user_id uuid,
                                    movie_id integer,
                                    rating integer
);


ALTER TABLE public.movierating OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 32929)
-- Name: movierating_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.movierating_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.movierating_id_seq OWNER TO postgres;

--
-- TOC entry 3447 (class 0 OID 0)
-- Dependencies: 226
-- Name: movierating_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.movierating_id_seq OWNED BY public.movierating.id;


--
-- TOC entry 225 (class 1259 OID 32885)
-- Name: movierole; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.movierole (
                                  id integer DEFAULT nextval('public.movie_id_seq'::regclass) NOT NULL,
                                  movieid integer,
                                  imdbid text NOT NULL,
                                  image text,
                                  actorname text NOT NULL,
                                  ascharacter text
);


ALTER TABLE public.movierole OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 32981)
-- Name: staffmember; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.staffmember (
                                    id integer NOT NULL,
                                    movieid bigint NOT NULL,
                                    imdbid character varying(255) NOT NULL,
                                    name character varying(255) NOT NULL,
                                    department text
);


ALTER TABLE public.staffmember OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 33026)
-- Name: staff_member_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.staffmember ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.staff_member_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 233 (class 1259 OID 33198)
-- Name: staffmemberrating; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.staffmemberrating (
                                          id bigint NOT NULL,
                                          user_id uuid,
                                          staffmember_id bigint,
                                          rating integer
);


ALTER TABLE public.staffmemberrating OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 33197)
-- Name: staffmemberrating_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.staffmemberrating ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.staffmemberrating_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 224 (class 1259 OID 32807)
-- Name: starshort; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.starshort (
                                  id integer NOT NULL,
                                  imdbid text NOT NULL,
                                  name text NOT NULL,
                                  movieid integer,
                                  staff_member_id integer
);


ALTER TABLE public.starshort OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 32806)
-- Name: starshort_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.starshort_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.starshort_id_seq OWNER TO postgres;

--
-- TOC entry 3448 (class 0 OID 0)
-- Dependencies: 223
-- Name: starshort_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.starshort_id_seq OWNED BY public.starshort.id;




--
-- TOC entry 222 (class 1259 OID 32793)
-- Name: writershort; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.writershort (
                                    id integer NOT NULL,
                                    imdbid text NOT NULL,
                                    name text NOT NULL,
                                    movieid integer,
                                    staff_member_id integer
);


ALTER TABLE public.writershort OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 32792)
-- Name: writershort_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.writershort_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.writershort_id_seq OWNER TO postgres;

--
-- TOC entry 3449 (class 0 OID 0)
-- Dependencies: 221
-- Name: writershort_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.writershort_id_seq OWNED BY public.writershort.id;


--
-- TOC entry 3246 (class 2604 OID 32961)
-- Name: characterrating id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.characterrating ALTER COLUMN id SET DEFAULT nextval('public.characterrating_id_seq'::regclass);


--
-- TOC entry 3241 (class 2604 OID 32782)
-- Name: directorshort id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.directorshort ALTER COLUMN id SET DEFAULT nextval('public.directorshort_id_seq'::regclass);


--
-- TOC entry 3240 (class 2604 OID 32773)
-- Name: movie id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movie ALTER COLUMN id SET DEFAULT nextval('public.movie_id_seq'::regclass);


--
-- TOC entry 3245 (class 2604 OID 32933)
-- Name: movierating id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movierating ALTER COLUMN id SET DEFAULT nextval('public.movierating_id_seq'::regclass);


--
-- TOC entry 3243 (class 2604 OID 32810)
-- Name: starshort id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.starshort ALTER COLUMN id SET DEFAULT nextval('public.starshort_id_seq'::regclass);


--
-- TOC entry 3242 (class 2604 OID 32796)
-- Name: writershort id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.writershort ALTER COLUMN id SET DEFAULT nextval('public.writershort_id_seq'::regclass);


--
-- TOC entry 3272 (class 2606 OID 32963)
-- Name: characterrating characterrating_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.characterrating
    ADD CONSTRAINT characterrating_pkey PRIMARY KEY (id);


--
-- TOC entry 3274 (class 2606 OID 32978)
-- Name: characterrating characterrating_user_id_movierole_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.characterrating
    ADD CONSTRAINT characterrating_user_id_movierole_id_key UNIQUE (user_id, movierole_id);



--
-- TOC entry 3258 (class 2606 OID 32786)
-- Name: directorshort directorshort_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.directorshort
    ADD CONSTRAINT directorshort_pkey PRIMARY KEY (id);


--
-- TOC entry 3254 (class 2606 OID 32976)
-- Name: movie movie_imdbid_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movie
    ADD CONSTRAINT movie_imdbid_key UNIQUE (imdbid);


--
-- TOC entry 3256 (class 2606 OID 32777)
-- Name: movie movie_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movie
    ADD CONSTRAINT movie_pkey PRIMARY KEY (id);


--
-- TOC entry 3268 (class 2606 OID 32935)
-- Name: movierating movierating_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movierating
    ADD CONSTRAINT movierating_pkey PRIMARY KEY (id);


--
-- TOC entry 3270 (class 2606 OID 32937)
-- Name: movierating movierating_user_id_movie_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movierating
    ADD CONSTRAINT movierating_user_id_movie_id_key UNIQUE (user_id, movie_id);


--
-- TOC entry 3264 (class 2606 OID 33291)
-- Name: movierole movierole_movieid_ascharacter_imdbid_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movierole
    ADD CONSTRAINT movierole_movieid_ascharacter_imdbid_key UNIQUE (movieid, ascharacter, imdbid);


--
-- TOC entry 3266 (class 2606 OID 32892)
-- Name: movierole movierole_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movierole
    ADD CONSTRAINT movierole_pkey PRIMARY KEY (id);


--
-- TOC entry 3276 (class 2606 OID 32985)
-- Name: staffmember staff_members_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.staffmember
    ADD CONSTRAINT staff_members_pkey PRIMARY KEY (id);


--
-- TOC entry 3278 (class 2606 OID 33202)
-- Name: staffmemberrating staffmemberrating_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.staffmemberrating
    ADD CONSTRAINT staffmemberrating_pkey PRIMARY KEY (id);


--
-- TOC entry 3280 (class 2606 OID 33204)
-- Name: staffmemberrating staffmemberrating_user_id_staffmember_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.staffmemberrating
    ADD CONSTRAINT staffmemberrating_user_id_staffmember_id_key UNIQUE (user_id, staffmember_id);


--
-- TOC entry 3262 (class 2606 OID 32814)
-- Name: starshort starshort_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.starshort
    ADD CONSTRAINT starshort_pkey PRIMARY KEY (id);


--
-- TOC entry 3248 (class 2606 OID 24602)
-- Name: authorities username_authority; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.authorities
    ADD CONSTRAINT username_authority UNIQUE (username, authority);


--
-- TOC entry 3250 (class 2606 OID 24604)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 3252 (class 2606 OID 24606)
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- TOC entry 3260 (class 2606 OID 32800)
-- Name: writershort writershort_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.writershort
    ADD CONSTRAINT writershort_pkey PRIMARY KEY (id);


--
-- TOC entry 3292 (class 2606 OID 32969)
-- Name: characterrating characterrating_movierole_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.characterrating
    ADD CONSTRAINT characterrating_movierole_id_fkey FOREIGN KEY (movierole_id) REFERENCES public.movierole(id);


--
-- TOC entry 3293 (class 2606 OID 32964)
-- Name: characterrating characterrating_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.characterrating
    ADD CONSTRAINT characterrating_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3284 (class 2606 OID 32787)
-- Name: directorshort directorshort_movieid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.directorshort
    ADD CONSTRAINT directorshort_movieid_fkey FOREIGN KEY (movieid) REFERENCES public.movie(id) ON DELETE CASCADE;


--
-- TOC entry 3285 (class 2606 OID 32986)
-- Name: directorshort directorshort_staff_member_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.directorshort
    ADD CONSTRAINT directorshort_staff_member_id_fkey FOREIGN KEY (staff_member_id) REFERENCES public.staffmember(id);


--
-- TOC entry 3283 (class 2606 OID 24607)
-- Name: authorities fk_authorities_users; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.authorities
    ADD CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES public.users(username);


--
-- TOC entry 3290 (class 2606 OID 32938)
-- Name: movierating movierating_movie_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movierating
    ADD CONSTRAINT movierating_movie_id_fkey FOREIGN KEY (movie_id) REFERENCES public.movie(id);


--
-- TOC entry 3291 (class 2606 OID 32943)
-- Name: movierating movierating_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movierating
    ADD CONSTRAINT movierating_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3294 (class 2606 OID 33205)
-- Name: staffmemberrating staffmemberrating_staffmember_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.staffmemberrating
    ADD CONSTRAINT staffmemberrating_staffmember_id_fkey FOREIGN KEY (staffmember_id) REFERENCES public.staffmember(id);


--
-- TOC entry 3295 (class 2606 OID 33210)
-- Name: staffmemberrating staffmemberrating_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.staffmemberrating
    ADD CONSTRAINT staffmemberrating_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3288 (class 2606 OID 32815)
-- Name: starshort starshort_movieid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.starshort
    ADD CONSTRAINT starshort_movieid_fkey FOREIGN KEY (movieid) REFERENCES public.movie(id) ON DELETE CASCADE;


--
-- TOC entry 3289 (class 2606 OID 32991)
-- Name: starshort starshort_staff_member_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.starshort
    ADD CONSTRAINT starshort_staff_member_id_fkey FOREIGN KEY (staff_member_id) REFERENCES public.staffmember(id);


--
-- TOC entry 3286 (class 2606 OID 32801)
-- Name: writershort writershort_movieid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.writershort
    ADD CONSTRAINT writershort_movieid_fkey FOREIGN KEY (movieid) REFERENCES public.movie(id) ON DELETE CASCADE;


--
-- TOC entry 3287 (class 2606 OID 32996)
-- Name: writershort writershort_staff_member_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.writershort
    ADD CONSTRAINT writershort_staff_member_id_fkey FOREIGN KEY (staff_member_id) REFERENCES public.staffmember(id);


-- Completed on 2023-01-16 23:21:14

--
-- PostgreSQL database dump complete
--

