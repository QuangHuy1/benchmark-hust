drop database if exists `benchmark-hust`;
use `benchmark-hust`;

create table if not exists `benchmark-hust`.school
(
    id            bigint auto_increment primary key,
    created_date  datetime(6)  null,
    updated_date  datetime(6)  null,
    abbreviations varchar(255) null,
    name          varchar(255) null
    );

create table if not exists `benchmark-hust`.faculty1
(
    id           bigint auto_increment primary key,
    created_date datetime(6)  null,
    updated_date datetime(6)  null,
    code         varchar(255) null,
    name         varchar(255) null,
    school_id    bigint       null,
    constraint foreign key (school_id) references `benchmark-hust`.school (id)
    );

create table if not exists `benchmark-hust`.`group`
(
    id           bigint auto_increment primary key,
    created_date datetime(6)  null,
    updated_date datetime(6)  null,
    code         varchar(255) null,
    subject1     varchar(255) null,
    subject2     varchar(255) null,
    subject3     varchar(255) null
    );

create table if not exists `benchmark-hust`.faculty_group
(
    faculty_id bigint not null,
    group_id   bigint not null,
    primary key (faculty_id, group_id),
    constraint unique (group_id),
    constraint foreign key (group_id) references `benchmark-hust`.`group` (id),
    constraint foreign key (faculty_id) references `benchmark-hust`.faculty (id)
    );

create table if not exists `benchmark-hust`.benchmark
(
    id           bigint auto_increment primary key,
    created_date datetime(6)  null,
    updated_date datetime(6)  null,
    mark         float        null,
    year         varchar(255) null,
    faculty_id   bigint       null,
    constraint foreign key (faculty_id) references `benchmark-hust`.faculty (id)
    );

create table if not exists `benchmark-hust`.user
(
    id           bigint auto_increment primary key,
    created_date datetime(6)  null,
    updated_date datetime(6)  null,
    full_name    varchar(255) null,
    password     varchar(255) null,
    username     varchar(255) null
    );