.PHONY: clean format

all: clean format upgrade

clean:
	@flutter pub get
	@echo "Cleaning..."
	@rm -rf pubspec.lock
	@flutter clean

format:
	@echo "Formatting..."
	@dart format .

upgrade: clean
	@echo "Upgrading dependencies..."
	@flutter pub upgrade
	@flutter pub outdated
