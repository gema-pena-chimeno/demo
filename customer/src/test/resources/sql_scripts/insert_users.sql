insert into users(id, user_name, password, role, active, version, created_at, last_updated)
values('000000000001', 'admin', '1234567890aB', 'ADMIN_ROLE', true, null, null, null) ON CONFLICT DO NOTHING ;
insert into users(id, user_name, password, role, active, version, created_at, last_updated)
values('000000000002', 'user_active', '1234567890aB', 'USER_ROLE', true, null, null, null) ON CONFLICT DO NOTHING;
insert into users(id, user_name, password, role, active, version, created_at, last_updated)
values('000000000002', 'user_inactive', '1234567890aB', 'USER_ROLE', false, null, null, null) ON CONFLICT DO NOTHING;