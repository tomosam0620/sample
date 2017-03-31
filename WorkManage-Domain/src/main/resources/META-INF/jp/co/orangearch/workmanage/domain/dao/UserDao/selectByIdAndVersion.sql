select
  /*%expand*/*
from
  user
where
  USER_ID = /* userId */'a'
  and
  VERSION = /* version */1
