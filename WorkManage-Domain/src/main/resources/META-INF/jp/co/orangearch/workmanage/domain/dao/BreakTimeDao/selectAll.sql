select
  /*%expand*/*
from
  BREAK_TIME
order by
  WORK_TIME_CD asc,
  BREAK_START_TIME asc
