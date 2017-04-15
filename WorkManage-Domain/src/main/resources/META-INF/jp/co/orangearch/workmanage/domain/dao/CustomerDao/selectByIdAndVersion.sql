select
  /*%expand*/*
from
  CUSTOMER
where
  CUSTOMER_ID = /* customerId */1
  and
  VERSION = /* version */1
