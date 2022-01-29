create table product_area_entity
(
    id       int auto_increment
        primary key,
    category varchar(255) null,
    name     varchar(255) null
);

create table project_entity
(
    id      int auto_increment
        primary key,
    creator varchar(255) null,
    name    varchar(255) null
);

create table product_entity
(
    id                int auto_increment
        primary key,
    comment           varchar(255) null,
    name              varchar(255) null,
    parent_product_id int          null,
    productarea       int          null,
    project           int          null,
    constraint FKd2i5ajodl9plfy10nijgb40du
        foreign key (project) references project_entity (id),
    constraint FKg04jjgud3ojvonjxparinrud8
        foreign key (parent_product_id) references product_entity (id),
    constraint FKrvnyux2jkwvv1dplv78ypqqr1
        foreign key (productarea) references product_area_entity (id)
);

create table rating_entity
(
    id         int auto_increment
        primary key,
    category   varchar(255) null,
    criterion  varchar(255) null,
    ratingarea int          null
);

create table product_rating_entity
(
    answer  varchar(255) null,
    comment varchar(255) null,
    score   int          null,
    product int          not null,
    rating  int          not null,
    primary key (product, rating),
    constraint FK9o1nvlik2lpi6xchb1n1nq5qr
        foreign key (rating) references rating_entity (id),
    constraint FKg4otyaavuivx9bucxge9j3080
        foreign key (product) references product_entity (id)
);

create table user_entity
(
    id       varchar(255) not null
        primary key,
    email    varchar(255) null,
    password varchar(255) null,
    username varchar(255) null
);

create table project_user_entity
(
    role    int          null,
    project int          not null,
    user    varchar(255) not null,
    primary key (project, user),
    constraint FKcxs9kbxta9ql5inmsh2w57jts
        foreign key (project) references project_entity (id),
    constraint FKnm8ev5nunxbxiko0cxvpx9oik
        foreign key (user) references user_entity (id)
);


