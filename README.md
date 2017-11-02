# PetProjectCountries

This pet-project shows skills of working with standart Android elements, RecyclerView, SQLite Retrofit 2 and Picasso.

This application is a small dictionary of countries and cities. It downloads country and city lists from site https://raw.githubusercontent.com/David-Haim/CountriesToCitiesJSON/master/countriesToCities.json. Then this data is parsed into local device database. List of countries is displayed in dropdown spinner which is filled from the local database. When user selects country from the spinner cities for specified country is displayed in ListView. Upon selecting city from the list user fetches info about this city in a separate view. Info about selected city is downloaded from Wikipedia search service http://www.geonames.org/export/wikipedia-webservice.html#wikipediaSearch and displayed in RecyclerView. User can remove items from RecylerView. After clicking on of the the items user will fetch its Web version on a separate screen.
