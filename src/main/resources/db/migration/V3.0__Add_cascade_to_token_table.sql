alter table token
   drop constraint fk_token_user;

alter table token
   add constraint fk_token_user
   foreign key (user_id) references users(id) on delete cascade;