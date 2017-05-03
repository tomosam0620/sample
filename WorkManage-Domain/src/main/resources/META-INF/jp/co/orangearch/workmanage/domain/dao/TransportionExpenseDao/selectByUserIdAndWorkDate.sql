select
  /*%expand*/*
from
  TRANSPORTION_EXPENSE
where
  USER_ID = /* userId */'a'
  and WORK_DATE = /* workDate */'2010-01-23'
order by
  TRANSPORT_NUMBER desc

