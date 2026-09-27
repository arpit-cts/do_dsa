# 04 · My Work and the Base Case

> After this part you can write the small piece of work that finishes the job, put it in the right spot
> (before, between or after the friends), and choose a base case that always works.

⬅️ [03 · Faith](03-faith.md) · 🏠 [Guide home](README.md) · [05 · The Call Stack](05-the-call-stack.md) ➡️

## Contents

1. [My work: meeting the expectation](#1-my-work-meeting-the-expectation)
2. [The base case: where faith stops](#2-the-base-case-where-faith-stops)
3. [Exercises](#3-exercises)
4. [One-minute recap](#4-one-minute-recap)

---

## 1. My work: meeting the expectation

After faith, only a small piece is left — **your own work**: the glue that turns the friend's answer into
**your** promise.

| Problem | The friend gives me | My work |
|---|---|---|
| sum(n) | 1 + … + (n − 1) | add n |
| factorial | (n − 1)! | multiply by n |
| count digits | the digits of n without its last digit | add 1 |
| palindrome | "the inside is a palindrome" | also check that the two outer letters match |
| biggest value by halves | the biggest on the left and on the right | take the bigger one |
| Tower of Hanoi | moving n − 1 disks (twice) | move the biggest disk, in between |

### The three spots: before, between, after

Your work can happen **before** asking the friend, **between** two friends, or **after** the friend
answered. The spot changes **the order** in which things happen:

```text
 one call:  [ BEFORE ] -> [ friend 1 ] -> [ BETWEEN ] -> [ friend 2 ] -> [ AFTER ]
               "pre"                         "in"                         "post"
           on the way in                                              on the way back
```

A tiny example with the same faith and the same base case:

- **Count down** — say n **before** asking the friend for n − 1 → n, n − 1, …, 1.
- **Count up** — ask the friend for n − 1 **first**, then say n **after** → 1, 2, …, n.

```text
 COUNT DOWN: say n BEFORE asking the friend

   countDown(3) ---> countDown(2) ---> countDown(1) ---> countDown(0)
      says 3            says 2            says 1            silent

   you hear 3, 2, 1: every word is said on the WAY IN, before the next friend starts


 COUNT UP: ask the friend FIRST, then say n

   countUp(3) -----> countUp(2) -----> countUp(1) -----> countUp(0)
      says 3 <--------- says 2 <--------- says 1 <--------- silent

   you hear 1, 2, 3: every word is said on the WAY BACK, after the friend has finished
```

Only the spot of "say n" moved, and the whole output turned around. You will see why in [part 05](05-the-call-stack.md).

---

## 2. The base case: where faith stops

The base case is the input that is **so small you just know the answer** — nobody needs to be asked.
Four rules:

1. **It keeps the same promise.** Check it against the expectation, like any other input!
2. **Every chain of friends reaches it.** The input must shrink and must not jump over it.
3. **Pick the smallest input the promise allows** — usually 0, an empty string, or "no node". It often
   removes special cases (sum(0) = 0 means you never need to handle sum(1) separately).
4. **Two different shrinks may need two base cases.** Fibonacci asks for n − 1 and n − 2, so it needs
   both fib(0) and fib(1).

| Mistake | What happens | Fix |
|---|---|---|
| no base case | friends forever → the stack overflows | add the smallest case |
| a base case that is never reached (n − 2 from an odd n jumps over 0) | friends forever | use "n ≤ 0", or add a second base case |
| a base case that breaks the promise (sum(1) = 0) | every answer is wrong | check it against the expectation |
| the friend's input is not smaller (f(n) asks f(n)) | friends forever | make the input shrink |

---

## 3. Exercises

**No code.** Write the four lines — EXPECTATION, FAITH, MY WORK, BASE CASE — or do what the question asks,
in your own words, before you open the answer. Compare the **ideas**, not the exact words.

**1.** Count down: say the numbers n, n − 1, …, 2, 1.

<details>
<summary>Answer</summary>

```text
 EXPECTATION  countDown(n) says n, n - 1, ..., 1 in that order (nothing when n = 0)
 FAITH        countDown(n - 1) says n - 1, ..., 1
 MY WORK      say n FIRST, then let the friend speak
 BASE CASE    n = 0: say nothing
```

Check n = 2: I say 2, the friend says 1 → "2, 1" ✅.

</details>

**2.** Count up: say the numbers 1, 2, …, n.

<details>
<summary>Answer</summary>

```text
 EXPECTATION  countUp(n) says 1, 2, ..., n in that order (nothing when n = 0)
 FAITH        countUp(n - 1) says 1, ..., n - 1
 MY WORK      let the friend speak FIRST, then say n
 BASE CASE    n = 0: say nothing
```

The same faith and base case as exercise 1 — only the **spot** of my work moved from before to after.
That is the "way in / way back" of [part 05](05-the-call-stack.md).

</details>

**3.** Factorial: n! = 1 × 2 × … × n.

<details>
<summary>Answer</summary>

```text
 EXPECTATION  fact(n) gives 1 x 2 x ... x n
 FAITH        fact(n - 1) gives 1 x 2 x ... x (n - 1)
 MY WORK      fact(n) = n x fact(n - 1)
 BASE CASE    fact(0) = 1
```

Why fact(0) = 1 and not 0? The base case must keep the promise **and** make the next step right:
fact(1) = 1 × fact(0) must be 1, so fact(0) must be 1 (the "empty product"). With 0, every answer would be 0.

</details>

**4.** Count the digits of a number n ≥ 0 (for example 5,207 has 4 digits).

<details>
<summary>Answer</summary>

```text
 EXPECTATION  digits(n) gives how many digits n has
 FAITH        digits(n without its last digit) counts the digits of that shorter number
 MY WORK      digits(n) = 1 + digits(n without its last digit)
 BASE CASE    n < 10 (only one digit): 1
```

Why not "digits(0) = 0" as the base case? Because 0 **has one digit** — that base case would break the
promise for n = 0. Always check the base case against the promise.

</details>

**5.** Find the **first** position of x in an array, or −1 if x is not there. Then: how would you find the
**last** position instead?

<details>
<summary>Answer</summary>

```text
 EXPECTATION  first(a, x, i) gives the smallest position k >= i with a[k] = x,
              or -1 if there is none
 FAITH        first(a, x, i + 1) gives that answer for the part after position i
 MY WORK      if a[i] is x: answer i (no friend needed!);
              otherwise: pass on the friend's answer
 BASE CASE    i = length: -1
```

The "−1" must be **in the promise**, so that you understand your friend's "not found".

**Last position:** ask the friend **first** (it searches the part after i). If the friend found something,
pass it on — it is further right than i. If the friend says −1, answer i when a[i] is x, otherwise −1.
My work moved from **before** the friend to **after** it.

</details>

**6.** Find the mistake in each plan and fix it:
(a) sum(n) = sum(n − 1) + n with base case "sum(1) = 1", and someone asks for sum(0).
(b) fact(n) = n × fact(n).
(c) countDown(n): say n, then countDown(n − 1); base case "n = 0: say 0".

<details>
<summary>Answer</summary>

- **(a)** sum(0) asks sum(−1), which asks sum(−2) … the chain **never reaches** 1 → friends forever.
  Fix: use the smallest allowed input, **sum(0) = 0** — it also makes sum(0) correct.
- **(b)** The friend's input is **not smaller** — it asks itself for the same n forever. Fix: fact(n − 1).
- **(c)** The promise was "n, …, 1", but this base case says an extra "0" — the base case **breaks the
  promise**. Fix: say nothing at n = 0 (or change the promise to "n, …, 0").

</details>

**7.** Say the digits of n, one at a time, using the friend "n without its last digit": (a) in order
(5207 → 5, 2, 0, 7) and (b) backwards (5207 → 7, 0, 2, 5). Where does "say the last digit" go in each?

<details>
<summary>Answer</summary>

```text
 EXPECTATION  forward(n) says the digits of n from left to right
 FAITH        forward(n without its last digit) says the other digits, left to right
 MY WORK      let the friend speak FIRST, then say the last digit
 BASE CASE    n < 10 (only one digit): say n
```

```text
 EXPECTATION  backward(n) says the digits of n from right to left
 FAITH        backward(n without its last digit) says the other digits, right to left
 MY WORK      say the last digit FIRST, then let the friend speak
 BASE CASE    n < 10 (only one digit): say n
```

Check 5207. Forward: the friend says 5, 2, 0 (trust it), then I say 7 ✅. Backward: I say 7, then the friend
says 0, 2, 5 ✅. The same friend and the same base case — only the **spot** of my work moved.

</details>

**8.** Choose the base case. For each promise, give the smallest input and its answer:
(a) length(s) gives the number of letters of s; (b) product(a, i) gives a[i] × a[i + 1] × … × the last
element; (c) biggest(a, i) gives the biggest value from a[i] to the end; (d) paths(r, c) counts the
right-or-down paths to the bottom-right cell of a grid; (e) subsets(list) gives how many subsets the list
has.

<details>
<summary>Answer</summary>

- **(a)** The empty string: 0.
- **(b)** i = length (nothing left): **1**, not 0 — the "empty product", just like fact(0) = 1. With 0, every
  product would become 0.
- **(c)** There is **no** answer for nothing: the biggest of no values doesn't exist. So the promise must say
  "at least one element", and the base case is **one element left** (i = the last position): a[i].
- **(d)** At the bottom-right cell: 1 (the path "don't move"); outside the grid: 0.
- **(e)** The empty list: **1** — it has exactly one subset, the empty one { }. Every new item then doubles
  the count, so n items have 2ⁿ subsets.

The smallest input is not always 0, and its answer is not always 0 — **the promise decides**.

</details>

**9.** A frog climbs n steps, jumping **1 or 3** steps at a time. Count the ways. Write the four lines with a
base case that also handles "jumped too far". How many ways are there for n = 5?

<details>
<summary>Answer</summary>

```text
 EXPECTATION  ways(n) gives the number of ways to climb exactly n steps
 FAITH        ways(n - 1) counts the ways that start with a 1-jump;
              ways(n - 3) counts the ways that start with a 3-jump
 MY WORK      ways(n) = ways(n - 1) + ways(n - 3)
 BASE CASE    n = 0: 1 (arrived: one way, do nothing);  n < 0: 0 (jumped too far)
```

With "n < 0: 0" you need no extra base cases for n = 1 and n = 2 — an impossible friend simply answers 0.
Filling a small table from the bottom: ways(0) = 1, ways(1) = 1, ways(2) = 1, ways(3) = 2, ways(4) = 3,
ways(5) = **4**. The 4 ways: 1+1+1+1+1, 1+1+3, 1+3+1 and 3+1+1 ✅.

</details>

**10.** Find the mistake in each plan and fix it:
(a) fib(n) = fib(n − 1) + fib(n − 2), with the only base case "fib(0) = 0".
(b) Fast power with the base case "n = 1: a", when someone asks for power(a, 0).
(c) arraySum(a, i) = a[i] + arraySum(a, i + 1), with the base case "i = the last position: a[i]", when
someone asks for the sum of an empty array.

<details>
<summary>Answer</summary>

- **(a)** fib(1) asks fib(0) **and fib(−1)**; fib(−1) asks fib(−2), and so on forever. Two shrinks (n − 1 and
  n − 2) need two base cases: fib(0) = 0 **and** fib(1) = 1.
- **(b)** power(a, 0) asks for half of 0 — which is **0 again**. The input doesn't shrink, so it never stops.
  Fix: the base case n = 0: 1. It also makes power(a, 1) = 1 × 1 × a = a work, so "n = 1" is not needed.
- **(c)** An empty array has no last position, and the very first step already reads a[0], which doesn't
  exist — the chain has jumped over its base case. Fix: the base case i = length: 0 (the empty sum). It
  works for every array, empty or not.

</details>

---

## 4. One-minute recap

- **My work** is the glue between the friend's answer and my promise.
- It happens **before** (on the way in), **between**, or **after** (on the way back) the friends — the spot
  decides the order of things.
- The **base case** keeps the promise, is always reached, and is usually the smallest input (0, empty,
  no node). Two different shrinks may need two base cases.

---

⬅️ [03 · Faith](03-faith.md) · 🏠 [Guide home](README.md) · [05 · The Call Stack](05-the-call-stack.md) ➡️
