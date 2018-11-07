CREATE ALIAS GENERATE_UUID FOR "com.payments.util.Random.uuid";

INSERT INTO users(id, first_name, last_name, payout_currency) VALUES
  (GENERATE_UUID(), 'Alan', 'Partridge', 'GBP'),
  (GENERATE_UUID(), 'Ron', 'Swanson', 'USD');
