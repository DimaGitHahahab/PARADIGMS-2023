prime(N) :- N > 1, \+ hasDivisors(N, 2).

divisible(A, B) :- 0 is A mod B.

hasDivisors(V, D) :- D * D =< V, (divisible(V, D), ! ; NextD is D + 1, hasDivisors(V, NextD)).

composite(N) :- \+ prime(N).

prime_divisors(1, []),!.
prime_divisors(N, [N]) :- prime(N), !.

prime_divisors(N, D) :- prime_divisors_helper(N, 2, D).

prime_divisors_helper(1, _, []).
prime_divisors_helper(N, Curr, [Curr | T]) :-
	N > 1,
	prime(Curr),
	divisible(N, Curr),
	N1 is N // Curr,
	prime_divisors_helper(N1, Curr, T).
prime_divisors_helper(N, Curr, A) :-
	CurrNext is Curr + 1,
	CurrNext =< N,
	prime_divisors_helper(N, CurrNext, A).

square_divisors(N, D) :- TrueN is N * N, prime_divisors(TrueN, D),!.