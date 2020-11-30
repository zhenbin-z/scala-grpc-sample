.PHONY: remove-database
remove-database:
	docker-compose up -d db
	cd grpc-server && sbt flywayClean

.PHONY: local-migrate
local-migrate:
	docker-compose up -d db 
	cd grpc-server && sbt -Dflyway.locations=db/migration,db/local flywayMigrate

.PHONY: generate-server
generate-server:
	cd grpc-server && sbt protocGenerate 

.PHONY: generate-client
generate-client:
	cd http-grpc-client && sbt protocGenerate 
