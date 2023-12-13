drop database if exists `benchmark_hust`;
create database benchmark_hust;
use `benchmark_hust`;

create table if not exists `benchmark_hust`.school
(
    id            bigint auto_increment primary key,
    created_date  datetime     null,
    updated_date  datetime     null,
    abbreviations varchar(255) null,
    vn_name       varchar(255) null,
    en_name       varchar(255) null
    );

create table if not exists `benchmark_hust`.faculty
(
    id           bigint auto_increment primary key,
    created_date datetime     null,
    updated_date datetime     null,
    code         varchar(255) null,
    name         varchar(255) null,
    school_id    bigint       null,
    constraint foreign key (school_id) references `benchmark_hust`.school (id)
    );

create table if not exists `benchmark_hust`.`group`
(
    id           bigint auto_increment primary key,
    created_date datetime     null,
    updated_date datetime     null,
    code         varchar(255) null,
    subject1     varchar(255) null,
    subject2     varchar(255) null,
    subject3     varchar(255) null
    );

create table if not exists `benchmark_hust`.faculty_group
(
    faculty_id bigint not null,
    group_id   bigint not null,
    primary key (faculty_id, group_id),
    constraint unique (group_id),
    constraint foreign key (group_id) references `benchmark_hust`.`group` (id),
    constraint foreign key (faculty_id) references `benchmark_hust`.faculty (id)
    );

create table if not exists `benchmark_hust`.benchmark
(
    id           bigint auto_increment primary key,
    created_date datetime null,
    updated_date datetime null,
    point_score  float    null,
    year_score   int      null,
    faculty_id   bigint   null,
    constraint foreign key (faculty_id) references `benchmark_hust`.faculty (id)
    );

create table if not exists `benchmark_hust`.user
(
    id           bigint auto_increment primary key,
    created_date datetime     null,
    updated_date datetime     null,
    full_name    varchar(255) null,
    password     varchar(255) null,
    username     varchar(255) null
    );