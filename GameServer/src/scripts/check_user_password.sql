select
(case
  when user_password = ? /* user password */ then 1
  else 0
end) value
from game_users
where user_id = ? /* user ID */