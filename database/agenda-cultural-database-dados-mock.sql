--
-- PostgreSQL database dump
--

-- Dumped from database version 17.0
-- Dumped by pg_dump version 17.0

-- Started on 2026-09-01 22:38:46

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4867 (class 0 OID 16455)
-- Dependencies: 224
-- Data for Name: categoria_evento; Type: TABLE DATA; Schema: public; Owner: agenda-cultural-user
--

INSERT INTO public.categoria_evento VALUES ('Show');
INSERT INTO public.categoria_evento VALUES ('Teatro');
INSERT INTO public.categoria_evento VALUES ('Baile');
INSERT INTO public.categoria_evento VALUES ('Festa');
INSERT INTO public.categoria_evento VALUES ('Outro');
INSERT INTO public.categoria_evento VALUES ('Palestra');
INSERT INTO public.categoria_evento VALUES ('Esporte');
INSERT INTO public.categoria_evento VALUES ('Exposição');


--
-- TOC entry 4861 (class 0 OID 16402)
-- Dependencies: 218
-- Data for Name: status_usuario; Type: TABLE DATA; Schema: public; Owner: agenda-cultural-user
--

INSERT INTO public.status_usuario VALUES ('Inativo');
INSERT INTO public.status_usuario VALUES ('Ativo');
INSERT INTO public.status_usuario VALUES ('Excluido');


--
-- TOC entry 4863 (class 0 OID 16408)
-- Dependencies: 220
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: agenda-cultural-user
--

INSERT INTO public.usuario VALUES (1, 'luccabibar@gmail.com', '57d047dbfa220d9df58085839e61f90f004c2b871cd6eb8d2db229cc9568e97e', 'bibar', 'Ativo', '2024-11-12');
INSERT INTO public.usuario VALUES (2, 'luccabibar+mod@gmail.com', '57d047dbfa220d9df58085839e61f90f004c2b871cd6eb8d2db229cc9568e97e', 'mod da silva', 'Ativo', '2024-11-12');
INSERT INTO public.usuario VALUES (10, 'luccabibar+teste@gmail.com', '4c4cbfbc74f7e321a2bb58e1d5c4545f5131b3b39721649473e64d723dc440c0', 'bibop', 'Ativo', '2025-08-02');
INSERT INTO public.usuario VALUES (17, 'caperongo+org@gmail.com', 'a9d9fb103854128b3943c73b4b8eb4f7e1d67efe6de8efa1ff449e00dc12eb43', 'orgperongo', 'Ativo', '2025-08-25');
INSERT INTO public.usuario VALUES (18, 'pessoa@gmail.com', '3f3408c21d6ba87381e1b62a5dc25414fc7003d1ecca8240613320e6d419fedf', 'pessoa da silva', 'Ativo', '2025-09-29');
INSERT INTO public.usuario VALUES (19, 'organizador@gmail.com', '3f3408c21d6ba87381e1b62a5dc25414fc7003d1ecca8240613320e6d419fedf', 'organizossauro', 'Ativo', '2025-09-29');
INSERT INTO public.usuario VALUES (20, 'sesquibauru@gmail.com', 'c1f9862898baecb31257785c8df05763f38567d300d156bb2c925929fa1c7c30', 'SESQUI Bauru', 'Ativo', '2025-10-14');
INSERT INTO public.usuario VALUES (21, 'cestodefruta@gov.com.br', '53226cdfb8cc7cacf597be0be773bf626434b1a5ba397cd9a3efebc85dd7c949', 'Prefeitura de Bauru', 'Ativo', '2025-11-18');
INSERT INTO public.usuario VALUES (22, 'tiojack@gmail.com', '0d41b989a51f9e8657ad2cbc74f551378b36563fa5c6df84c7a49835cfb05a0c', 'Uncle JACK', 'Ativo', '2025-11-18');
INSERT INTO public.usuario VALUES (23, 'armazem@gmail.com', '2f44eb7a18b5c7218eae239372d2d9e1a3fb2c9e1b0cbe8ac7d1c440d2b60d5c', 'Armazenas', 'Ativo', '2025-11-18');
INSERT INTO public.usuario VALUES (24, 'voodoo@gmail.com', 'fc42048176c429a34dc05ce9cc83a7a73973741fbd602add705a03700039ea90', 'Vudu Lounge Pub', 'Ativo', '2025-11-18');
INSERT INTO public.usuario VALUES (25, 'caada@unesp.br', '4d02046c2ca4909f60843007b025929f846f22bfd2185c49ea3510d87b0bdd6c', 'CAADA Unesp', 'Ativo', '2025-11-18');


--
-- TOC entry 4866 (class 0 OID 16443)
-- Dependencies: 223
-- Data for Name: moderador; Type: TABLE DATA; Schema: public; Owner: agenda-cultural-user
--

INSERT INTO public.moderador VALUES (2, '17178477814');


--
-- TOC entry 4865 (class 0 OID 16431)
-- Dependencies: 222
-- Data for Name: organizador; Type: TABLE DATA; Schema: public; Owner: agenda-cultural-user
--

INSERT INTO public.organizador VALUES (1, '16016106850');
INSERT INTO public.organizador VALUES (17, '41623962056');
INSERT INTO public.organizador VALUES (19, '31872057000104');
INSERT INTO public.organizador VALUES (20, '62294188000185');
INSERT INTO public.organizador VALUES (21, '66990259000135');
INSERT INTO public.organizador VALUES (22, '40104043000131');
INSERT INTO public.organizador VALUES (23, '25302173000195');
INSERT INTO public.organizador VALUES (24, '65859619000100');
INSERT INTO public.organizador VALUES (25, '72956745000130');


--
-- TOC entry 4868 (class 0 OID 16479)
-- Dependencies: 225
-- Data for Name: regiao_evento; Type: TABLE DATA; Schema: public; Owner: agenda-cultural-user
--

INSERT INTO public.regiao_evento VALUES ('Bauru');
INSERT INTO public.regiao_evento VALUES ('Grande São Paulo');


--
-- TOC entry 4860 (class 0 OID 16397)
-- Dependencies: 217
-- Data for Name: status_evento; Type: TABLE DATA; Schema: public; Owner: agenda-cultural-user
--

INSERT INTO public.status_evento VALUES ('EmAnalise');
INSERT INTO public.status_evento VALUES ('Aprovado');
INSERT INTO public.status_evento VALUES ('Reprovado');
INSERT INTO public.status_evento VALUES ('Cancelado');


--
-- TOC entry 4870 (class 0 OID 16518)
-- Dependencies: 227
-- Data for Name: evento; Type: TABLE DATA; Schema: public; Owner: agenda-cultural-user
--

INSERT INTO public.evento VALUES (15, 'Aprovado', 'Entrega do TCC', 'Ao soar da sétima trombeta, o presente trabalho será entregue e a penitência será consumada.', 'Outro', 'alto.png', 'https://github.com/luccabibar', 1, 2, '2025-11-18 19:00:00', '2025-11-19 00:00:00', 'Bauru', 'Av. Eng. Luiz Edmundo C. Coube 14-01', '"null"', '2025-10-08');
INSERT INTO public.evento VALUES (12, 'Aprovado', 'Aniversário do Lucca :D', 'Você acaba de ser oficialmente CONVIDADO para a confraternização  novembrina anual popularmente conhecida como MEU ANIVERSÁRIO', 'Festa', 'K\KM6ZU3EZRon4dZOUYMFIWPQg.png', 'https://github.com/luccabibar/', 1, 2, '2025-11-18 14:00:00', '2025-11-18 22:00:00', 'Bauru', 'R. Augusto João Costa', 'https://maps.app.goo.gl/ECeNwWvRNbaFQnmXA', '2024-11-26');
INSERT INTO public.evento VALUES (9, 'EmAnalise', 'High School, o Musical', 'A peça revive a nostalgia de High School Musical, além das emoções, sentimentos e descobertas que vão acontecer durante as aulas, treinos de basquete e corredores do colégio.', 'Teatro', 'NOTFOUND.png', 'contato', 1, 2, '2024-11-23 19:00:00', '2024-11-19 23:00:00', 'Bauru', 'Aqui', 'https://maps.app.goo.gl/ECeNwWvRNbaFQnmXA', '2024-11-26');
INSERT INTO public.evento VALUES (11, 'EmAnalise', 'Futefamília Sesc', 'Recreação livre de futebol em minicampo para famílias jogarem juntas. Sejam crianças, adolescentes ou pessoas adultas.', 'Esporte', 'NOTFOUND.png', 'contato', 1, 2, '2024-11-30 10:15:00', '2024-11-30 11:20:00', 'Bauru', 'Aqui', 'https://maps.app.goo.gl/ECeNwWvRNbaFQnmXA', '2024-11-26');
INSERT INTO public.evento VALUES (7, 'EmAnalise', 'Ao Rei do Baião', 'O grupo bauruense TRIO CAJUÍNA, composto por Lara Grossi (voz), Giu Mastrelli (zabumba e voz), Rômulo Querubin (sanfona e voz) e Jess Leal (triângulo e percussão) apresenta um repertório totalmente dedicado ao Rei do Baião.', 'Show', 'NOTFOUND.png', 'contato', 1, 2, '2024-11-30 16:30:00', '2024-11-30 18:00:00', 'Bauru', 'Aqui', 'https://maps.app.goo.gl/ECeNwWvRNbaFQnmXA', '2024-11-26');
INSERT INTO public.evento VALUES (19, 'Aprovado', 'EBONY Show "KM2"', 'A rapper e compositora nascida em Queimados, cidade da Baixada Fluminense (RJ), Ebony, apresenta seu mais recente álbum: KM2.', 'Show', 'O\Ob6E4d5n28niUuJUMzCZdwT2.png', 'https://www.sescsp.org.br/', 20, 2, '2025-11-22 20:00:00', '2025-11-22 22:00:00', 'Bauru', 'R. Aureliano Cardia', '"null"', '2025-11-08');
INSERT INTO public.evento VALUES (21, 'Aprovado', 'Tons Afro', 'Apresentando releituras de Itamar, Serena e Anelis Assumpção, Tons Afro une ancestralidade afro-brasileira e a força da música independente. O show celebra a espiritualidade, a resistência e a inventividade da família Assumpção.', 'Show', 'Q\Q7gcuuDSIyQCxtMXVzDmaz2J.png', 'https://www.sescsp.org.br/', 20, 2, '2025-11-23 16:30:00', '2025-11-23 18:30:00', 'Bauru', 'Av. Aureliano Cardia, 6-71', '"null"', '2025-11-18');
INSERT INTO public.evento VALUES (22, 'Aprovado', 'Espetáculo Cabaças ', 'Através do fruto “cabaça” uma figura mística conduz a jovem Umzimba a um mergulho nas memórias e forças da sua história. Inspirado na tradição da etnia Xhosa sul-africana, o espetáculo é uma celebração da vida e do poder da herança ancestral.', 'Teatro', 'q\qevJ6v1y6LfVmR0WDCXOjTE5.png', 'https://www.sescsp.org.br/', 20, 2, '2025-11-21 21:00:00', '2025-11-21 22:30:00', 'Bauru', 'Av. Aureliano Cardia, 6-71', '"null"', '2025-11-18');
INSERT INTO public.evento VALUES (23, 'Aprovado', 'Dançando com a história', 'Funk, soul, groove e outros ritmos da música negra embalam danças livres ou coreografadas e trazem a experiência de fruição na pista expressando a identidade de cada corpo. Pra dançar ou pra ouvir boa música, o público é convidado a vir pro baile.', 'Baile', '5\5sIahSD0p30KWUfr0eP2JgBa.png', 'https://www.sescsp.org.br/', 20, 2, '2025-11-20 16:30:00', '2025-11-20 18:00:00', 'Bauru', 'Av. Aureliano Cardia, 6-71', '"null"', '2025-11-18');
INSERT INTO public.evento VALUES (28, 'Aprovado', 'Ensaio Sobre a Beleza', 'Quinto dia da exposição Ensaio Sobre a Beleza”, do artista visual José Antônio Garbino. A exposição inédita no município reúne peças que compõe acervos de diversos colecionadores e obras inéditas do artista. ', 'Exposição', '6\6BK2H5FXtfcV0eABHqsLUjkv.png', 'https://sites.bauru.sp.gov.br/', 21, 2, '2025-11-21 08:00:00', '2025-11-21 17:00:00', 'Bauru', 'Rua Antônio Alves, 9-10', '"null"', '2025-11-18');
INSERT INTO public.evento VALUES (24, 'Aprovado', 'Ensaio Sobre a Beleza', 'Abertura da exposição Ensaio Sobre a Beleza”, do artista visual José Antônio Garbino. A exposição inédita no município reúne peças que compõe acervos de diversos colecionadores e obras inéditas do artista.', 'Exposição', '9\9yI0Pog3uO2cj3IoGBJuEmH7.png', 'https://sites.bauru.sp.gov.br/', 21, 2, '2025-11-17 20:00:00', '2025-11-17 22:00:00', 'Bauru', 'Rua Antônio Alves, 9-10', '"null"', '2025-11-18');
INSERT INTO public.evento VALUES (25, 'Aprovado', 'Ensaio Sobre a Beleza', 'Segundo dia da exposição Ensaio Sobre a Beleza”, do artista visual José Antônio Garbino. A exposição inédita no município reúne peças que compõe acervos de diversos colecionadores e obras inéditas do artista. ', 'Exposição', 's\sYXeLEO6WLaDgoNswSgfYQrw.png', 'https://sites.bauru.sp.gov.br/', 21, 2, '2025-11-18 08:00:00', '2025-11-18 17:00:00', 'Bauru', 'Rua Antônio Alves, 9-10', '"null"', '2025-11-18');
INSERT INTO public.evento VALUES (26, 'Aprovado', 'Ensaio Sobre a Beleza', 'Terceiro dia da exposição Ensaio Sobre a Beleza”, do artista visual José Antônio Garbino. A exposição inédita no município reúne peças que compõe acervos de diversos colecionadores e obras inéditas do artista. ', 'Exposição', 'r\rDi7ofiDnLOGKFZP03kRrk8d.png', 'https://sites.bauru.sp.gov.br/', 21, 2, '2025-11-19 08:00:00', '2025-11-19 17:00:00', 'Bauru', 'Rua Antônio Alves, 9-10', '"null"', '2025-11-18');
INSERT INTO public.evento VALUES (27, 'Aprovado', 'Ensaio Sobre a Beleza', 'Quarto dia da exposição Ensaio Sobre a Beleza”, do artista visual José Antônio Garbino. A exposição inédita no município reúne peças que compõe acervos de diversos colecionadores e obras inéditas do artista. ', 'Exposição', 'k\kU5nJcVcPcPQd1Zjc9nMkpGd.png', 'https://sites.bauru.sp.gov.br/', 21, 2, '2025-11-20 08:00:00', '2025-11-20 17:00:00', 'Bauru', 'Rua Antônio Alves, 9-10', '"null"', '2025-11-18');
INSERT INTO public.evento VALUES (29, 'Aprovado', 'Ensaio Sobre a Beleza', 'Último dia da exposição Ensaio Sobre a Beleza”, do artista visual José Antônio Garbino. A exposição inédita no município reúne peças que compõe acervos de diversos colecionadores e obras inéditas do artista. ', 'Exposição', 'd\dIAKsaGsn3GF9jAdpJIEOEvI.png', 'https://sites.bauru.sp.gov.br/', 20, 2, '2025-11-22 08:00:00', '2025-11-22 15:00:00', 'Bauru', 'Rua Antônio Alves, 9-10', '"null"', '2025-11-18');
INSERT INTO public.evento VALUES (20, 'Aprovado', 'SEUN KUTI & EGYPT 80', 'Acompanhado da banda Egypt 80, fundada por
seu pai - o lendário Fela Kuti, criador do afrobeat -,
Seun apresenta seu álbum mais recente "Heavier
Yet", produzido pelo músico Lenny Kravitz e pelo
engenheiro de som Sodi Marciszewer', 'Show', 's\sGvmuZRxYII2TZ7oeTHbPZlM.png', 'https://www.sescsp.org.br/', 20, 2, '2025-11-19 20:00:00', '2025-11-19 22:00:00', 'Bauru', 'R. Aureliano Cardia', '"null"', '2025-11-08');
INSERT INTO public.evento VALUES (30, 'Aprovado', 'Toca Aí!', 'TOCA AÍ é o festival de bandas do  espaço musical “Thiago Ortigosa”, uma chance incrível dos alunos vivenciarem um show de verdade!', 'Show', 't\tgK2uguQxw7DA760DMQqrFfm.png', 'https://www.tiojackbauru.com.br/', 22, 2, '2025-11-20 18:00:00', '2025-11-20 23:00:00', 'Bauru', 'Av. Duque de Caxias', '"null"', '2025-11-18');
INSERT INTO public.evento VALUES (31, 'Aprovado', 'Linkin Park (cove)', 'repare-se para sentir a energia, a emoção e o impacto de Linkin Park, num show que recria todas as eras do grupo que marcou gerações.', 'Show', 'a\aaJoWLjOdpge6JXfV9iMtjqO.png', 'https://www.tiojackbauru.com.br/', 22, 2, '2025-11-22 20:00:00', '2025-11-23 02:00:00', 'Bauru', 'Av. Duque de Caxias', '"null"', '2025-11-18');
INSERT INTO public.evento VALUES (32, 'Aprovado', 'ARMAZÉM 45 ANOS', 'Novembro de aniversário, o Armazén Bar comemora 45 anos e segue pesado no rock e na experiência ao vivo!
Quarta-feira na nossa Armagenda', 'Show', 'z\zF0Kj2XqFalVvDPpIt4YDjzg.png', '@armazen_bar', 23, 2, '2025-11-19 20:00:00', '2025-11-20 02:00:00', 'Bauru', 'Rua Quintino Bocaiúva, 2-20', '"null"', '2025-11-18');
INSERT INTO public.evento VALUES (33, 'Aprovado', 'ARMAZÉM 45 ANOS ', 'Novembro de aniversário, o Armazén Bar comemora 45 anos e segue pesado no rock e na experiência ao vivo! 
Sexta-feira na nossa Armagenda', 'Show', 'q\q9QpVSnOPlo2RlYisYC4rH38.png', '@armazen_bar', 23, 2, '2025-11-21 20:00:00', '2025-11-22 02:00:00', 'Bauru', 'Rua Quintino Bocaiúva, 2-20', '"null"', '2025-11-18');
INSERT INTO public.evento VALUES (34, 'Aprovado', 'ARMAZÉM 45 ANOS', 'ovembro de aniversário, o Armazén Bar comemora 45 anos e segue pesado no rock e na experiência ao vivo! 
Sabadão na nossa Armagenda', 'Show', 'e\eU9dujsyCfnDDrzsdnhoAc9k.png', '@armazen_bar', 23, 2, '2025-11-22 20:00:00', '2025-11-23 02:00:00', 'Bauru', 'Rua Quintino Bocaiúva, 2-20', '"null"', '2025-11-18');
INSERT INTO public.evento VALUES (35, 'Aprovado', 'Sons de Jorge', 'Isso mesmo, a banda mais preta, mais bonita, mais groovada da cidade tá na área pra fazer um set de brasilidades.', 'Show', 'e\e6yKppPS1RrjQAFLIIsBd7B3.png', '@voodooloungepub', 24, 2, '2025-11-21 20:00:00', '2025-11-22 02:00:00', 'Bauru', 'Rua Antônio Alves 34-61', '"null"', '2025-11-18');
INSERT INTO public.evento VALUES (36, 'Aprovado', 'Banda Jazz Groove', 'Se liga nesse quarteto mais que fantástico!

@adrianomartinsmusica  @renatoalvesguitar  @rogerepereira  @raelsamjazz ', 'Show', 'T\TbhlIDmBtfke0qW8ZjFhrGmk.png', 'voodooloungepub', 24, 2, '2025-11-19 20:00:00', '2025-11-20 02:00:00', 'Bauru', 'Rua Antônio Alves 34-61', '"null"', '2025-11-18');
INSERT INTO public.evento VALUES (37, 'Aprovado', 'ASFIXIA COLERA ROMERO', 'Sim, vai ter a segunda edição!!!

@colera.banda @asfixiasocial e @romero_punkrock 

Uma noite para ficar na história novamente!', 'Show', 'g\g1wxJm3jqwmJn0D6XBPbfv87.png', 'voodooloungepub', 24, 2, '2025-11-22 20:00:00', '2025-11-23 02:00:00', 'Bauru', 'Rua Antônio Alves 34-61', '"null"', '2025-11-18');
INSERT INTO public.evento VALUES (8, 'EmAnalise', ' Rockphonic 2024', 'O evento gratuito, acontece no Casarão da Paulista, e ocupará um palco voltado para a rua, permitindo que o público acompanhe os shows diretamente da Avenida Paulista.', 'Show', 'NOTFOUND.png', 'contato', 1, 2, '2024-11-24 11:00:00', '2024-11-24 19:00:00', 'Grande São Paulo', 'Aqui', 'https://maps.app.goo.gl/ECeNwWvRNbaFQnmXA', '2024-11-26');
INSERT INTO public.evento VALUES (10, 'EmAnalise', 'Festival TODAS', 'O Festival TODAS desembarca no Parque Horto Florestal para uma experiência única que celebra a força e o talento das mulheres.', 'Teatro', 'NOTFOUND.png', 'contato', 1, 2, '2024-12-07 08:00:00', '2024-12-07 20:00:00', 'Grande São Paulo', 'Aqui', 'https://maps.app.goo.gl/ECeNwWvRNbaFQnmXA', '2024-11-26');
INSERT INTO public.evento VALUES (17, 'EmAnalise', 'RAFA QUEIROZ', 'Acompanhado de banda, o cantor Rafa Queiroz
celebra a história da música soul com uma
viagem pela década de 1970, homenageando
dois gingantes da música negra que marcaram
gerações: Marvin Gaye e Stevie Wonder.', 'Show', 'NOTFOUND.png', 'https://www.sescsp.org.br/', 20, 2, '2025-10-29 20:00:00', '2025-10-29 22:00:00', 'Bauru', 'R. Aureliano Cardia', '"null"', '2025-10-14');
INSERT INTO public.evento VALUES (16, 'EmAnalise', 'JÔ MOURA CANTA TIM MAIA', 'A cantora e compositora bauruense traz para o
palco uma homenagem ao legado de Tim Maia,
uma das principais referências do soul no Brasil,
em um show cheio de swing e romantismo.', 'Show', 'NOTFOUND.png', 'https://www.sescsp.org.br/', 20, 2, '2025-11-01 16:30:00', '2025-11-01 20:00:00', 'Grande São Paulo', 'Av. Paulista', '"null"', '2025-10-14');
INSERT INTO public.evento VALUES (18, 'EmAnalise', 'AQUELE COM PÉS INCHADOS', 'O herói trágico dá lugar ao errante contemporâneo, perdido entre destinos, algoritmos, obstinado na busca desmedida de si. Entre poeira e memória, o espetáculo lança uma pergunta: é possível escapar daquilo que nos habita?', 'Teatro', 'NOTFOUND.png', 'https://www.sescsp.org.br/', 20, 2, '2025-11-01 16:30:00', '2025-11-01 18:00:00', 'Bauru', 'R. Aureliano Cardia', '"null"', '2025-10-14');
INSERT INTO public.evento VALUES (14, 'Aprovado', 'Silksong AMANHA', 'Junte-se a milhares de milhoes de fas alucinados para esperar o lancamento da sequencia do videogame hit de 2017 hollow knight', 'Outro', 'k\k6G0XSycfVaHcZc4zEZo46B2.png', 'https://www.teamcherry.com.au/', 17, 2, '2025-09-03 22:30:00', '2025-09-04 00:00:00', 'Bauru', 'Pq. Vitoria regia', '"null"', '2025-08-27');
INSERT INTO public.evento VALUES (38, 'Cancelado', 'Game Night Ubaiano', 'Fala Galera! 
Estão preparados para encerrar o semestre da melhor maneira possível?
Venha jogar com a gente, no  Ubaiano, em frente a unesp!
', 'Festa', 'L\L45HQc3QEnEKVySCpvjwcgSa.png', 'instagram.com/caadaunesp', 25, 2, '2025-11-28 19:00:00', '2025-11-28 23:00:00', 'Bauru', ' Av. Eng. Carijo Coube', '"null"', '2025-11-18');
INSERT INTO public.evento VALUES (39, 'Cancelado', 'Game Night Ubaiano', 'Fala Galera! 
Estão preparados para encerrar o semestre da melhor maneira possível?
Venha jogar com a gente, no  Ubaiano, em frente a Unesp!', 'Festa', '1\14a6AhqgulGt9Y5TT7BzIDVv.png', ' instagram.com/caadaunesp', 25, 2, '2025-11-28 19:00:00', '2025-11-29 03:00:00', 'Bauru', ' Av. Eng. Carijo Coube', '"null"', '2025-11-18');


--
-- TOC entry 4872 (class 0 OID 16553)
-- Dependencies: 229
-- Data for Name: atualizacao_evento; Type: TABLE DATA; Schema: public; Owner: agenda-cultural-user
--

INSERT INTO public.atualizacao_evento VALUES (3, 12, 'Atualizacao', 'ATENÇÃO a festa vai cair dia 18 de novembro. mudou a data. SE LIGA EM.', NULL, '2024-11-26');
INSERT INTO public.atualizacao_evento VALUES (4, 12, 'Att 2', 'Seguinte! a festa ja foi, mas esta eh uma atualizacao de teste', NULL, '2025-07-25');
INSERT INTO public.atualizacao_evento VALUES (8, 14, 'ATENCAO', 'SILKSONG AMANHA eh HOJE. AMANHA teremos SILKSONG, e SILKSONG AMANHA tera sido ONTEM.', NULL, '2025-09-03');
INSERT INTO public.atualizacao_evento VALUES (11, 14, 'ALERTA', 'SILKSONG AMANHA foi ONTEM. HOJE eh SILKSONG HOJE.', NULL, '2025-09-04');
INSERT INTO public.atualizacao_evento VALUES (12, 15, 'Polêmicas da celebração', 'Supostamente existe uma celebração novembrina que coincide com A Data de Entrega. A Instituição REJEITA qualquer outra atividade nesta data e em qualquer dia anterior.', NULL, '2025-10-12');
INSERT INTO public.atualizacao_evento VALUES (13, 16, 'Reagendamento', 'O Evento foi reagendado: de Sábado dia 20 de Outubro, às 16h30, para Quarta dia 24 de Outubro, as 20h00', NULL, '2025-10-18');
INSERT INTO public.atualizacao_evento VALUES (14, 20, 'Adiamento', 'O show foi adiado, e acontecerá dia 19/11/2025, no mesmo horário.', NULL, '2025-11-18');
INSERT INTO public.atualizacao_evento VALUES (15, 37, 'Ad', 'ATENÇÃO! O show teve de ser adiado e ocorrerá no dia 22/11/2025!', NULL, '2025-11-18');
INSERT INTO public.atualizacao_evento VALUES (18, 39, 'Aviso sobre controles', 'Atenção: devido à alta demanda, recomendamos que tragam seus próprios controles para jogar com a gente!', NULL, '2025-11-18');


--
-- TOC entry 4864 (class 0 OID 16421)
-- Dependencies: 221
-- Data for Name: pessoa; Type: TABLE DATA; Schema: public; Owner: agenda-cultural-user
--

INSERT INTO public.pessoa VALUES (10);
INSERT INTO public.pessoa VALUES (18);


--
-- TOC entry 4878 (class 0 OID 0)
-- Dependencies: 228
-- Name: atualizacao_evento_id_seq; Type: SEQUENCE SET; Schema: public; Owner: agenda-cultural-user
--

SELECT pg_catalog.setval('public.atualizacao_evento_id_seq', 18, true);


--
-- TOC entry 4879 (class 0 OID 0)
-- Dependencies: 226
-- Name: evento_id_seq; Type: SEQUENCE SET; Schema: public; Owner: agenda-cultural-user
--

SELECT pg_catalog.setval('public.evento_id_seq', 39, true);


--
-- TOC entry 4880 (class 0 OID 0)
-- Dependencies: 219
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: agenda-cultural-user
--

SELECT pg_catalog.setval('public.usuario_id_seq', 25, true);


-- Completed on 2026-09-01 22:38:46

--
-- PostgreSQL database dump complete
--

