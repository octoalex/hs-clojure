deploy-app:
	docker-compose config > docker-compose-resolved.yaml && kompose convert -f docker-compose-resolved.yaml
	kubectl apply -f patients-db-deployment.yaml,patients-db-service.yaml,patientsapp-deployment.yaml,patientsapp-service.yaml,volume-pg-hs-persistentvolumeclaim.yaml 