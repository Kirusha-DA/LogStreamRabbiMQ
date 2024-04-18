rabbit:
	docker run --rm --hostname localhost --name rabbitmq -p 8000:15672 -p 5672:5672 rabbitmq:3-management
	