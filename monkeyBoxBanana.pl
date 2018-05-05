% monkeyBoxesBanana
% pm pc1 pc2 hc1 hc2  hm
% 0   1   2   3   3   3  (inicial)
% 3   3   3   3   6   9  (final)   - c2 encima  de caja1
% 3   3   3   6   3   9  (final)   - c1 encima  de caja2


solucion(A):- sgte(e(0,1,2,3,3,3),[e(0,1,2,3,3,3)],_,[],A).

pertenece(H,[H|_]).
pertenece(H,[_|R]):- pertenece(H,R).


sgte(e(3,3,3,3,6,9),L,L,A,A).
sgte(e(3,3,3,6,3,9),L,L,A,A).
sgte(e(Pm,Pc1,Pc2,Hc1,Hc2,Hm),Le,L,La,A):- accion(e(Pm,Pc1,Pc2,Hc1,Hc2,Hm),e(Pms,Pc1s,Pc2s,Hc1s,Hc2s,Hms),Acc),
	                      not(pertenece(e(Pms,Pc1s,Pc2s,Hc1s,Hc2s,Hms),Le)),
			      sgte(e(Pms,Pc1s,Pc2s,Hc1s,Hc2s,Hms),[e(Pms,Pc1s,Pc2s,Hc1s,Hc2s,Hms)|Le],L,[Acc|La],A).



%ir a caja 1 solo
accion(e(Pm,Pc1,Pc2,Hc1,Hc2,Hm),e(Pc1,Pc1,Pc2,Hc1,Hc2,Hm),'a'):-
	not(Pm = Pc1),
	(Pm = 3 ; Pm = 0).

%ir a caja 2 solo
accion(e(Pm,Pc1,Pc2,Hc1,Hc2,Hm),e(Pc2,Pc1,Pc2,Hc1,Hc2,Hm),'b'):-
	not(Pm = Pc2),
	(Pm = 3 ; Pm = 0).

%llevar caja 1 a la posicion caja 2
%accion(e(Pm,Pc1,Pc2,Hc1,Hc2,Hm),e(Pc2,Pc2,Pc2,Hc1,Hc2,Hm),'c'):-
%	Pm is Pc1,
%	not(pc1 = 3),
%       not(Pc1 = Pc2).


%llevar caja 2 a la posicion caja 1
%accion(e(Pm,Pc1,Pc2,Hc1,Hc2,Hm),e(Pc1,Pc1,Pc1,Hc1,Hc2,Hm),'d'):-
%	Pm is Pc2,
%	not(pc2 = 3),
%	not(Pc1 = Pc2).


%llevar c1 dejajod e la banana
accion(e(Pm,Pc1,Pc2,Hc1,Hc2,Hm),e(3,3,Pc2,Hc1,Hc2,Hm),'e'):-
        not(Pc1 = 3),
        Pm is Pc1.


%llevar c2 dejajod e la banana
accion(e(Pm,Pc1,Pc2,Hc1,Hc2,Hm),e(3,Pc1,3,Hc1,Hc2,Hm),'f'):-
        not(Pc2 = 3),
        Pm is Pc2.


%montar caja 1 sobra c2
accion(e(Pm,Pc1,Pc2,Hc1,Hc2,Hm),e(Pm,Pc1,Pc2,6,3,Hm),'g'):-
	Pm is 3,
	Pc1 is 3,
	Pc2 is 3,
	Hc1 is 3,
	Hc2 is 3.

%montar caja 1 sobra c2
accion(e(Pm,Pc1,Pc2,Hc1,Hc2,Hm),e(Pm,Pc1,Pc2,3,6,Hm),'h'):-
	Pm is 3,
	Pc1 is 3,
	Pc2 is 3,
	Hc1 is 3,
	Hc2 is 3.

%trepar caja 1
accion(e(Pm,Pc1,Pc2,Hc1,Hc2,Hm),e(Pm,Pc1,Pc2,Hc1,Hc2, Hms),'i'):-
	Pm is 3,
	Pc1 is 3,
	Pc2 is 3,
	Hc1 is Hm,
	Hms is (3 + Hc1).

%trepar caja 2
accion(e(Pm,Pc1,Pc2,Hc1,Hc2,Hm),e(Pm,Pc1,Pc2,Hc1,Hc2,Hms),'j'):-
	Pm is 3,
	Pc1 is 3,
	Pc2 is 3,
	Hc2 is Hm,
	Hms is (3 + Hc2).










