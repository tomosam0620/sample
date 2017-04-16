select
  /*%expand*/*
from
  TRANSPORTION_EXPENSE
where
  USER_ID = /* userId */'a'
  and WORK_DATE >= /* from */'2017-01-01'
  and WORK_DATE <= /* to */'2017-01-01'
