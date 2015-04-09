F = load('plot.txt');
length = size(F);
graph1 = subplot(2,1,1);
graph2 = subplot(2,1,2);
q = 1:length(2);
%y = 1000 - x*1000./100000;
y = 1000./(1+exp(8*q./length(2)));
plot(graph1, F,'r')
title(graph1,'Iteration - Path length')
ylabel(graph1,'Path length')
xlabel(graph1,'Iteration')

plot(graph2,y,'r')
title(graph2,'Temperature schedule')
ylabel(graph2,'Temperature')
xlabel(graph2,'Iteration')
