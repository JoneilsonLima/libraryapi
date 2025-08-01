create table autor (
    id uuid not null primary key,
    nome varchar(100) not null,
    data_nascimento date not null,
    nacionalidade varchar(50) not null,
    data_cadastro timestamp,
    data_atualizacao timestamp,
    id_usuario uuid
);

create table livro (
    id uuid not null primary key,
    isbn varchar(20) not null,
    titulo varchar(150) not null,
    data_publicacao date not null,
    genero varchar(30) not null,
    preco numeric(18,2),
    data_cadastro timestamp,
    data_atualizacao timestamp,
    id_usuario uuid,
    id_autor uuid not null references autor(id),
    constraint chk_genero check (genero in ('FICCAO', 'FANTASIA', 'MISTERIO', 'ROMANCE', 'BIOGRAFIA', 'CIENCIA'))
);
