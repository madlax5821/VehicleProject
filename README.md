# this is a springboot application  to implement ...

local environment parameter
-Ddatabase.driver=org.postgresql.Driver
-Ddatabase.dialect=org.hibernate.dialect.PostgreSQL9Dialect
-Ddatabase.url=jdbc:postgresql://localhost:5432/windowsDB
-Ddatabase.user=admin
-Ddatabase.password=123456
-Dsecret.key=88888888
-Daws.s3.bucket=debug-14-test3
-Daws.region=us-east-1
-Daws.accessKeyId=AKIA3KDQ4VLMIREHZXMB
-Daws.secretKey=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
-Dspring.profiles.active=dev123

interview requirements:

Section 1: docker, image, container;

Section 2: flyway migrate (common command: mvn flyway:clean flyway:migrate);

Section 3: git operation;

Section 4: whole programm establishment:
1. Persistent Layer (connect to database directly)
2. Service Layer (set up configuration to clients command)
3. Controller Layer (first layer facing to client)
