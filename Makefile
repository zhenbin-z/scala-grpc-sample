.PHONY: generate-server
generate-server:
	cd grpc-server && sbt protocGenerate 

.PHONY: generate-client
generate-client:
	cd http-grpc-client && sbt protocGenerate 
