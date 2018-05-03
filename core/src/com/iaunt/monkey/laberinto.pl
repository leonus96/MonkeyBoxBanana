% Autor:
% Fecha: 03/05/2015

%laberinto
solucion(A):- sgte(e(1,1),[e(1,1)],_,[],A).



sgte(e(1,4),V,V,A,A):-!.
sgte(e(X,Y),Vs,V,As,A):- accion(e(X,Y),e(Xs,Ys),Accion),
                         not(muro(e(Xs,Ys))),
                         not(pertenece(e(Xs,Ys),Vs)),
                         sgte(e(Xs,Ys),[e(Xs,Ys)|Vs],V,[Accion|As],A).

%derecha
accion(e(X,Y),e(Z,Y),'d'):- Z is X + 1,
                           X < 4.
%izquierda
accion(e(X,Y),e(Z,Y),'i'):- Z is X - 1,
                           X > 1.
%subir
accion(e(X,Y),e(X,Z),'s'):- Z is Y + 1,
                            Y < 4.
%bajar
accion(e(X,Y),e(X,Z),'b'):- Z is Y - 1,
                            Y > 1.

pertenece(X,[X|_]):-!.
pertenece(X,[_|R]):- pertenece(X,R).

muro(e(1,2)):-!.
muro(e(2,2)):-!.
muro(e(4,4)):-!.
muro(e(4,2)):-!.
muro(e(2,4)):-!.
