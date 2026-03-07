BUCKET_NAME := rasc-select-demo

.PHONY: demo-up
demo-up:
	aws-vault exec home -- aws s3 mb s3://$(BUCKET_NAME)
	curl https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.csv -o all_month.csv
	tar czf all_month.csv.tar.gz all_month.csv
	aws-vault exec home -- aws s3 cp all_month.csv s3://$(BUCKET_NAME)
	aws-vault exec home -- aws s3 cp all_month.csv.tar.gz s3://$(BUCKET_NAME)
	curl https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/pokedex.json -o pokedex.json
	aws-vault exec home -- aws s3 cp pokedex.json s3://$(BUCKET_NAME)
	bzip2 pokedex.json
	aws-vault exec home -- aws s3 cp pokedex.json.bz2 s3://$(BUCKET_NAME)
	rm all_month*
	rm pokedex*
	
.PHONY: demo-down
demo-down:
	aws-vault exec home -- aws s3 rb --force s3://$(BUCKET_NAME)

.PHONY: ls
ls:
	aws-vault exec home -- aws s3 ls s3://$(BUCKET_NAME)