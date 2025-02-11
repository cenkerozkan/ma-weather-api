from pymongo import MongoClient
import json

def file_helper():
    with open("../data/weather_db.cities.json") as f:
        return json.loads(f.read())

def init_mongo():
    client: MongoClient = MongoClient("mongodb://admin:ma_webapi_2025@localhost:27017")
    mock_data: list = file_helper()
    
    # Create Database.
    db = client.get_database("weather_db")

    # Create cities collection.
    try:
        db.create_collection("cities")
        cities = db.get_collection("cities")
        # Insert Data.
        for i in mock_data:
            cities.insert_one(i)
    except Exception as e:
        print(e)

    # Create air_quality collection.
    try:
        db.create_collection("air_quality")
    except Exception as e:
        print(e)


if __name__ == "__main__":
    init_mongo()