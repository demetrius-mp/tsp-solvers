# tsp-solvers

Trying to provide efficient implementations of solutions
to the Traveling Salesman Problem.

## Purpose
I have a repository with 2 solutions for the TSP, written in Python for a college work.
But due to the heavy and repetitive math work (calculating distances),
it happened to run very slowly.

So I ported it to Java, and tuned it as best as I could
(i.e reducing time and space complexity).

My goal is to enchance my skills on the development of efficient algorithms,
and also, to learn more algorithms, especially the ones that helps
on NP-Hard problems.

## How to contribute
- The `instances` directory has some instance examples.
- The `utils.FileHandler` has a method to read the instance and
return a `Solution` object.
    - Attention: the returned object does not have the distance field properly set.

Feel free to contribute with a pull request, issue, or suggestion!

## Already implement solutions:
- ## Metaheuristics
    - Hill Climbing
    - Simulated Annealing

## Possible new features
- Genetic algorithms, implementing many options of selection, crossover,
mutation, and replacement.
- Brute force algorithm
- Generate image with the found solution