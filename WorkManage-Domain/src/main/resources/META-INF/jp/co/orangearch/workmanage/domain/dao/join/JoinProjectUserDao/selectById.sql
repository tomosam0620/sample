select
	U.USER_ID,
	U.ROLE_ID,
	U.AFFILIATION,
	U.POSITION,
	U.USER_ID,
	J.PROJECT_ID
from user U
left outer join JOIN_PROJECT J on
	U.USER_ID = J.USER_ID
where
	U.USER_ID = /* userId */1234567

