all-products-schema.json
  JSON Schema for validating the response of the "Get All Products" API.

  - Ensures that the response contains a list of product objects with essential fields like id, title, price, rating, etc.
  - Each product must follow a defined structure to ensure data consistency.
  - Validates overall response structure including pagination metadata (total, skip, limit).
  - Useful for schema validation in automated API tests using Rest Assured or other tools.

product-schema.json
  JSON Schema for validating the "Get Product by ID" API response.

  - Ensures that each individual product response includes mandatory fields like:
    id, title, price, rating, stock, brand, and category.
  - Used for schema validation in automated tests (e.g., with Rest Assured).
  - Helps maintain consistent structure for product object responses across environments.
