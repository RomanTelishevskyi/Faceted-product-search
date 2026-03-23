I decided not to use arrays for the product but to use extra relations in the DB.

I've tried fetching data from what was given by the task API endpoint, but it did not reliably give me the necessary information,
and so I imported their CSV file and used it to create the DB. (got 10,000 products with not empty name and picture,
could've sorted for not empty categories and brands, but didn't think it was necessary)

In the future, I would change how brands, categories, and products are stored in the database. (mainly case sensitivity and empty spaces)

In the retrospective I understand that I should have taken the ids from the API, not generate them myself + it would simplify the data model.

For the product-searching decided to make a function in the PostgerSQL with 2d-array of filters
but later on found out that it wasn't the best solution, since Postgres doesn't work well with 2d-arrays and I could've used a JSONB or just concatenated 1d-array.

Though it is better to have two functions for searching products and calculating facets, especially after I added the offset. That adds an extra request, but it keeps the product list, filter counts, and pagination easier.

Added limits to brands and categories that are shown on the screen, since otherwise they would be too long.

To scale this further, I would:

* Maybe add caching for repeated filter combinations.
* I would change how partial search works, meaning that it would search only after you typed the whole phrase
  or at least with a little delay, that would reduce the number of unnecessary requests. (but that doesn't matter right now + I like it more this way).
* Better version of pagination (cursor-based)

I'm not sure what to write as an edge-case, so here are a few:

* Products with multiple brands and/or categories are counted only once.
* Facets are counted with all the products in the DB(with the filters), not with those on screen.
