insert into user_info(id, user_name, password, role, active, version, created_at, last_updated)
values('000000000001', 'admin', '1234567890aB', 'ADMIN', true, 0, null, null) ON CONFLICT DO NOTHING ;
insert into user_info(id, user_name, password, role, active, version, created_at, last_updated)
values('000000000002', 'user_active', '1234567890aB', 'USER', true, 0, null, null) ON CONFLICT DO NOTHING;
insert into user_info(id, user_name, password, role, active, version, created_at, last_updated)
values('000000000002', 'user_inactive', '1234567890aB', 'USER', false, 0, null, null) ON CONFLICT DO NOTHING;