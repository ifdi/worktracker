create table if not exists token
(
    token varchar not null,
    user_id bigint,
    constraint token_pkey primary key (token),
    constraint fk_token_user foreign key (user_id) references users(id)
);