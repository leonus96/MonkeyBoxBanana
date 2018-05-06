% monkeyBoxesBanana
% pm e pc1 pc2 pb  hc1  hc2  hm
% 7  0  2  12   8   3   3   3  (inicial)
% 8  0  8   8   8   3   6   9  (final)   - c2 encima  de caja1
% 8  0  8   8   8   6   3   9  (final)   - c1 encima  de caja2


solucion(A,V):- sgte(e(7,0,2,12,8,3,3,3),[e(7,0,2,12,8,3,3,3)],V,[],A).

pertenece(H,[H|_]).
pertenece(H,[_|R]):- pertenece(H,R).


sgte(e(8,0,8,8,8,6,3,9),L,L,A,A).
sgte(e(8,0,8,8,8,3,6,9),L,L,A,A).

sgte(e(Pm,E,Pc1,Pc2,Pb,Hc1,Hc2,Hm),Le,L,La,A):- accion(e(Pm,E,Pc1,Pc2,Pb,Hc1,Hc2,Hm),e(Pms,Es,Pc1s,Pc2s,Pbs,Hc1s,Hc2s,Hms),Acc),
	                      not(pertenece(e(Pms,Es,Pc1s,Pc2s,Pbs,Hc1s,Hc2s,Hms),Le)),
			      sgte(e(Pms,Es,Pc1s,Pc2s,Pbs,Hc1s,Hc2s,Hms),[e(Pms,Es,Pc1s,Pc2s,Pbs,Hc1s,Hc2s,Hms)|Le],L,[Acc|La],A).



%ir derecha
accion(e(Pm,E,Pc1,Pc2,Pb,Hc1,Hc2,Hm),e(Pms,E,Pc1,Pc2,Pb,Hc1,Hc2,Hm),'a'):-
        (E=0),
        Pms is Pm+1,
        (Pm < 13),
        ((Hm =:=3 );( Hc1=:=Hm-3,Pms=:=Pc1 ); (Hc2=:=Hm-3 , Pms =:= Pc2) ).

%ir izquierda
accion(e(Pm,E,Pc1,Pc2,Pb,Hc1,Hc2,Hm),e(Pms,E,Pc1,Pc2,Pb,Hc1,Hc2,Hm),'b'):-
        (E=0),
        Pms is Pm-1,
        (Pm > 0),
        ((Hm =:=3 );( Hc1=:=Hm-3,Pms=:=Pc1 ); (Hc2=:=Hm-3 , Pms =:= Pc2) ).


%levantar caja 1 derecha
accion(e(Pm,E,Pc1,Pc2,Pb,Hc1,Hc2,Hm),e(Pm,1,Pc1s,Pc2,Pb,Hc1,Hc2,Hm),'c'):-
        Pc1=\=Pb,
        E = 0,
	Pc1 =:= Pm+1,
        Pc1s is Pm,
	(Pc1 =:= Pm-1 ; Pc1 =:= Pm+1).

%levantar caja 1 izquierda
accion(e(Pm,E,Pc1,Pc2,Pb,Hc1,Hc2,Hm),e(Pm,1,Pc1s,Pc2,Pb,Hc1,Hc2,Hm),'d'):-
        Pc1=\=Pb,
        E = 0,
	Pc2 =:= Pm-1,
	Pc1s is Pm,
	(Pc1 =:= Pm-1 ; Pc1 =:= Pm+1).


%levantar caja 2 derecha
accion(e(Pm,E,Pc1,Pc2,Pb,Hc1,Hc2,Hm),e(Pm,2,Pc1,Pc2s,Pb,Hc1,Hc2,Hm),'e'):-
        Pc2=\=Pb,
        E =:= 0,
	Pc2 =:= Pm+1,
        Pc2s is Pm,
	(Pc2 =:= Pm-1 ; Pc2 =:= Pm+1).
%levantar caja 2 izquierda
accion(e(Pm,E,Pc1,Pc2,Pb,Hc1,Hc2,Hm),e(Pm,2,Pc1,Pc2s,Pb,Hc1,Hc2,Hm),'f'):-
        Pc2=\=Pb,
        E =:= 0,
	Pc2 =:= Pm+1,
        Pc2s is Pm,
	(Pc2 =:= Pm-1 ; Pc2 =:= Pm+1).

%llevar caja1 derecha
accion(e(Pm,E,_,Pc2,Pb,Hc1,Hc2,Hm),e(Pms,E,Pc1s,Pc2,Pb,Hc1,Hc2,Hm),'g'):-
        E = 1,
        Pm<13,
        Pms is Pm+1,
        Pc1s is Pms,
        Pms=\=Pb.

% llevar caja1 izquierda
accion(e(Pm,E,_,Pc2,Pb,Hc1,Hc2,Hm),e(Pms,E,Pc1s,Pc2,Pb,Hc1,Hc2,Hm),'h'):-
        E = 1,
        Pm>0,
        Pms is Pm-1,
        Pc1s is Pms,
        Pms=\=Pb.
%llevar caja2 derecha
accion(e(Pm,E,Pc1,_,Pb,Hc1,Hc2,Hm),e(Pms,E,Pc1,Pc2s,Pb,Hc1,Hc2,Hm),'i'):-
        E = 2,
        Pm<13,
        Pms is Pm+1,
        Pc2s is Pms,
        Pms=\=Pb.

% llevar caja2 izquierda
accion(e(Pm,E,Pc1,_,Pb,Hc1,Hc2,Hm),e(Pms,E,Pc1,Pc2s,Pb,Hc1,Hc2,Hm),'j'):-
        E = 2,
        Pm>0,
        Pms is Pm-1,
        Pc2s is Pms,
        Pms=\=Pb.


% soltar caja1 a la derecha
accion(e(Pm,E,_,Pc2,Pb,Hc1,Hc2,Hm),e(Pm,Es,Pc1s,Pc2,Pb,Hc1,Hc2,Hm),'k'):-
       Pc2=\=Pb,
       E is 1,
       Es=0,
       Pc1s is Pm+1,
       Pm is Pb-1.

%soltar caja1 a la izquierda
accion(e(Pm,E,_,Pc2,Pb,Hc1,Hc2,Hm),e(Pm,Es,Pc1s,Pc2,Pb,Hc1,Hc2,Hm),'l'):-
       Pc2=\=Pb,
       E is 1,
       Es=0,
       Pc1s is Pm-1,
       Pm is Pb+1.

% soltar caja2 a la derecha
accion(e(Pm,E,Pc1,_,Pb,Hc1,Hc2,Hm),e(Pm,Es,Pc1,Pc2s,Pb,Hc1,Hc2,Hm),'m'):-
       Pc1=\=Pb,
       E is 2,
       Es=0,
       Pc2s is Pm+1,
       Pm is Pb-1.

%soltar caja2 a la izquierda
accion(e(Pm,E,Pc1,_,Pb,Hc1,Hc2,Hm),e(Pm,Es,Pc1,Pc2s,Pb,Hc1,Hc2,Hm),'n'):-
       Pc1=\=Pb,
       E is 2,
       Es=0,
       Pc2s is Pm-1,
       Pm is Pb+1.


%apilar c1 encima de c2 por la derecha
accion(e(Pm,E,_,Pc2,Pb,_,Hc2,Hm),e(Pm,Es,Pc1s,Pc2,Pb,Hc1s,Hc2,Hm),'o'):-
       E =:= 1,
       Es is 0,
       Hc2 =:= 3,
       Pc1s is Pc2,
       Hc1s is 6,
       Pm =:= Pc2-1.

%apilar c1 encima de c2 por la zuquierda
accion(e(Pm,E,_,Pc2,Pb,_,Hc2,Hm),e(Pm,Es,Pc1s,Pc2,Pb,Hc1s,Hc2,Hm),'p'):-
       E =:= 1,
       Es is 0,
       Hc2 =:= 3,
       Pc1s is Pc2,
       Hc1s is 6,
       Pm is Pc2+1.

%apilar c2 encima de c1 por la derecha
accion(e(Pm,E,Pc1,_,Pb,Hc1,_,Hm),e(Pm,Es,Pc1,Pc2s,Pb,Hc1,Hc2s,Hm),'q'):-
       E =:= 2,
       Es is 0,
       Hc1 =:= 3,
       Pc2s is Pc1,
       Hc2s is 6,
       Pm =:= Pc1-1.


%apilar c2 encima de c1 por la zuquierda
accion(e(Pm,E,Pc1,_,Pb,Hc1,_,Hm),e(Pm,Es,Pc1,Pc2s,Pb,Hc1,Hc2s,Hm),'r'):-
       E =:= 2,
       Es is 0,
       Hc1 =:= 3,
       Pc2s is Pc1,
       Hc2s is 6,
       Pm is Pc1+1.

%trepar c1 hacia la derecha
accion(e(Pm,0,Pc1,Pc2,Pb,Hc1,Hc2,Hm),e(Pm,0,Pc1,Pc2,Pb,Hc1,Hc2,Hms),'s'):-
       Hc1 =:= Hm,
       Hms is Hc1+3,
       Pm =:= Pc1-1.

%trepar c1 hacia la izquierda
accion(e(Pm,0,Pc1,Pc2,Pb,Hc1,Hc2,Hm),e(Pm,0,Pc1,Pc2,Pb,Hc1,Hc2,Hms),'t'):-
       Hc1 =:= Hm,
       Hms is Hc1+3,
       Pm =:= Pc1+1.


%trepar c2 hacia la derecha
accion(e(Pm,0,Pc1,Pc2,Pb,Hc1,Hc2,Hm),e(Pm,0,Pc1,Pc2,Pb,Hc1,Hc2,Hms),'u'):-
       Hc2 =:= Hm,
       Hms is Hc2+3,
       Pm =:= Pc2-1.

%trepar c2 hacia la ziquierda
accion(e(Pm,0,Pc1,Pc2,Pb,Hc1,Hc2,Hm),e(Pm,0,Pc1,Pc2,Pb,Hc1,Hc2,Hms),'v'):-
       Hc2 =:= Hm,
       Hms is Hc2+3,
       Pm =:= Pc2+1.



























