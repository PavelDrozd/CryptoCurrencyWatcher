insert into cryptocurrencies (id, symbol, name)
values (90, 'BTC', 'Bitcoin'),
		(80, 'ETH', 'Ethereum'),
		(48543, 'SOL', 'Solana');

insert into users (username, cryptocurrency_id, price_at_register, register_date)
values ('test', 90, 25000, '2022-01-01');