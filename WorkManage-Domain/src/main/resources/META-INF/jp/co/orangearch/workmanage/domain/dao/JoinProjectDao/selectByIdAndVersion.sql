select
  /*%expand*/*
from
  JOIN_PROJECT
where
  USER_ID = /* userId */'a'
  and
  PROJECT_ID = /* projectId */1
  and
  START_DATE = /* startDate */'2010-01-23'
  and
  VERSION = /* version */1
