--------------------------------------------------------------------------------
|    _____                      __             _    __      _                  |
|   |  __ \                    / /            | |   \ \    (_)                 |
|   | |  \/ ___ _ __   ___    | |   _ __      | |_   | |    _  ___             |
|   | | __ / _ \ '_ \ / _ \  / /   | '__|     | __|   \ \  | |/ __|            |
|   | |_\ \  __/ | | |  __/  \ \   | |     _  | |_    / /  | | (__             |
|    \____/\___|_| |_|\___|   | |  |_|    ( )  \__|  | |   |_|\___|            |
|                              \_\        |/        /_/                        |
|                                                                              |
|                                                                              |
--------------------------------------------------------------------------------
| Written by Thomas Schaffer <thomas.schaffer@epitech.eu> in Java.             |
| Licensed under the MIT license.                                              |
--------------------------------------------------------------------------------

--------------------------------------------------------------------------------
| Description                                                                  |
--------------------------------------------------------------------------------

Gene{r,t}ic is a somewhat generic framework for genetic algorithms
applications written in Java.

All the evolution logic is handled by the main GeneticAlgorithm class and
a few delegate helper classes (Candidate selector, Population generator...).
Helper classes are abstracted through interfaces and one or several
implementations are included for each.

The implementation completely abstracts away the internal representation
of candidate solutions AND the genetic operators that operate them,
allowing for reasonably generic code.

Implementing concrete candidates along with their specialized operators,
and a suitable fitness evaluation function is the responsability of the user.
Example implementations of generic candidates (binary form,
real numbers set, ...) are included, in which case they only need a fitness
evaluation function.
