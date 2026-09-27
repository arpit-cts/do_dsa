# 09 · Final Test

> After this test you know — not hope — that you can design recursion and backtracking with faith and
> expectation. 25 mixed questions, from easy to hard.

⬅️ [08 · Backtracking](08-backtracking.md) · 🏠 [Guide home](README.md)

## Contents

1. [How to take the test](#1-how-to-take-the-test)
2. [The test](#2-the-test)
3. [Your score](#3-your-score)

---

## 1. How to take the test

This is the last step. Treat it like a real exam:

1. **Paper and pen only.** No code, and no peeking at the parts.
2. **Write your answer first**, then open the answer. For most questions that means the four lines, checked
   on a tiny input.
3. **Score yourself honestly** after each question:
   - **2 points:** the right idea, and all four lines right (or every fact the question asks for);
   - **1 point:** the right idea with one small slip, like a base case that is off by one;
   - **0 points:** the idea is wrong or missing.
4. **Time:** about 2 hours. Split it into two sittings if you like: questions 1–13, then 14–25.

The questions go from easy to hard, and each one says which part it tests, like *(part 04)*. There are
25 questions and 50 points.

---

## 2. The test

### Warm-up

**1.** *(part 01)* Add up all the whole numbers from a to b — for example 3 + 4 + 5 when a = 3 and b = 5.
Write the four lines.

<details>
<summary>Answer</summary>

```text
 EXPECTATION  add(a, b) gives a + (a + 1) + ... + b, or 0 if a > b
 FAITH        add(a + 1, b) gives (a + 1) + ... + b
 MY WORK      a + the friend's answer
 BASE CASE    a > b (nothing to add): 0
```

Check add(3, 5): the friend gives add(4, 5) = 9 (trust it) → 3 + 9 = 12 ✅.

</details>

**2.** *(part 02)* Turn each fuzzy promise into a clear one: (a) "search does the finding" (is x in an
array?); (b) "maxDepth handles the tree"; (c) "countWays solves the stairs" (1 or 2 steps at a time).

<details>
<summary>Answer</summary>

- **(a)** has(a, x, i) says yes if x appears in a[i], a[i + 1], …, the last element.
- **(b)** depth(node) gives the number of levels of the tree that starts at node; 0 for no node.
- **(c)** ways(n) gives the number of different ways to climb exactly n steps, taking 1 or 2 steps at a time.

</details>

**3.** *(part 03)* Finish the job, trusting the friends: (a) power(2, 10) with the half-friend, which says
power(2, 5) = 32; (b) the number of nodes of a tree whose two sides have 4 and 6 nodes; (c) the biggest of
3, 9, 4, 7 by halves, where the friends say 9 and 7; (d) the length of the word "recursion", where the
friend says the length of "ecursion" is 8.

<details>
<summary>Answer</summary>

- **(a)** 10 is even, so 32 × 32 = **1024**.
- **(b)** 1 + 4 + 6 = **11** (don't forget the node itself).
- **(c)** The bigger of 9 and 7: **9**.
- **(d)** 1 + 8 = **9**.

</details>

**4.** *(part 04)* Multiply the digits of n, for example 234 → 2 × 3 × 4 = 24. Choose the base case carefully.

<details>
<summary>Answer</summary>

```text
 EXPECTATION  prod(n) gives the product of the digits of n
 FAITH        prod(n without its last digit) gives the product of the other digits
 MY WORK      the last digit x the friend's answer
 BASE CASE    n < 10 (only one digit): n
```

Check 234: the friend gives prod(23) = 6, and 4 × 6 = 24 ✅. Why not "n = 0: 1"? Then prod(0) would say 1,
but the number 0 has the digit 0, so its product is 0. And "n = 0: 0" would make **every** product 0.
The one-digit base case keeps the promise for every n.

</details>

**5.** *(part 04)* Print the letters of a word **backwards**, one at a time ("abc" → c, b, a), with the friend
handling the word **without its first letter**. Does "print the first letter" go before or after the friend?

<details>
<summary>Answer</summary>

```text
 EXPECTATION  back(s) prints the letters of s from the last to the first
 FAITH        back(s without its first letter) prints the rest, from its last letter
 MY WORK      let the friend print FIRST, then print the first letter of s
 BASE CASE    an empty word: print nothing
```

**After.** Check "abc": the friend prints c, b (trust it), then I print a ✅. Printing the first letter
**before** the friend would print the word forwards.

</details>

**6.** *(part 05)* Draw the pile of plates for digits(4,321) (how many digits?) at the moment the base case
runs, and write what each plate will do when its friend answers.

<details>
<summary>Answer</summary>

```text
 +----------------+
 |  digits(4)     |  <- running: one digit, the base case answers 1
 |  digits(43)    |  waiting; will give back 1 + 1 = 2
 |  digits(432)   |  waiting; will give back 1 + 2 = 3
 |  digits(4321)  |  waiting; will give back 1 + 3 = 4   <- the first call
 +----------------+
```

4 plates, and the answer is 4.

</details>

**7.** *(part 05)* How tall does the pile of plates get? (a) count(a, x, i) on an array of 50 elements,
starting at i = 0; (b) the biggest value by halves in an array of 64 elements; (c) fib(10).

<details>
<summary>Answer</summary>

- **(a)** **51** plates: i = 0, 1, …, 50 (the last one is the empty part).
- **(b)** **7** plates: parts of 64, 32, 16, 8, 4, 2 and 1 elements.
- **(c)** **10** plates: the longest path is fib(10) → fib(9) → … → fib(1). The tree has 177 boxes, but only
  one path is on the pile at a time.

</details>

### The middle

**8.** *(part 06)* The tree of fib(5): how many boxes, how many leaves, and how tall is it? Don't draw it —
use what you know about the trees of fib(4) (9 boxes, 5 leaves) and fib(3) (5 boxes, 3 leaves).

<details>
<summary>Answer</summary>

fib(5) asks fib(4) and fib(3), so its tree is **one box plus the two smaller trees**:

- boxes: 1 + 9 + 5 = **15**;
- leaves: 5 + 3 = **8** (the root itself is not a leaf);
- height: 1 + the taller side (fib(4) is 4 levels) = **5**.

You just used faith to count a tree: trust the smaller trees, add the root.

</details>

**9.** *(part 06)* Here is a tree of calls, root at the bottom:

```text
                U         V
                ▲         ▲
                └────┬────┘
                     │
                     S              T
                     ▲              ▲
                     └──────┬───────┘
                            │
      Q                     R
      ▲                     ▲
      └──────────┬──────────┘
                 │
                 P
```

(a) Write the pre order (a box speaks when the ant arrives). (b) Write the post order (a box speaks when
the ant leaves for good). (c) Which boxes are on the pile when V speaks?

<details>
<summary>Answer</summary>

- **(a)** **P, Q, R, S, U, V, T**
- **(b)** **Q, U, V, S, T, R, P**
- **(c)** The path from the root to V: **P, R, S, V**.

</details>

**10.** *(part 07)* Name the pattern: (a) count the leaves of a binary tree; (b) is an array a mirror, like
1, 2, 3, 2, 1?; (c) the number of right-or-down paths in a grid; (d) the sum of the digits of n; (e) find a
value in a sorted array.

<details>
<summary>Answer</summary>

**(a)** trees; **(b)** two ends; **(c)** counting the ways; **(d)** one step smaller; **(e)** halves (asking
only one half).

</details>

**11.** *(part 07)* Count the leaves of a binary tree.

<details>
<summary>Answer</summary>

```text
 EXPECTATION  leaves(node) gives the number of leaves in the tree at node
 FAITH        leaves(left child) and leaves(right child) count each side
 MY WORK      if node has no children: 1; otherwise add the two answers
 BASE CASE    no node: 0
```

The leaf rule sits in **my work**, not in the base case: a leaf is a real node, not an empty tree.

</details>

**12.** *(part 07)* The **minimum depth** of a binary tree is the number of levels on the shortest path from
the root to a **leaf**. Why is "1 + the smaller of the two answers" wrong for this tree? Fix it.

```text
              3
           a leaf
              ▲
              │
              │
              2
        no left child
              ▲
              │
              │
        1 (the root)
        no left child
```

<details>
<summary>Answer</summary>

At the root, the left side is empty (0) and the right side says 2, so "1 + the smaller" gives 1 + 0 = 1 —
but the root is **not** a leaf! An empty side has no leaf in it, so it must not be used.

```text
 EXPECTATION  minDepth(node) gives the number of levels on the shortest path
              from node to a leaf (0 for no node)
 FAITH        minDepth(left child) and minDepth(right child) are correct
 MY WORK      two children: 1 + the smaller answer;
              only one child: 1 + that child's answer;
              no children: 1
 BASE CASE    no node: 0
```

For this tree: 3 is a leaf → 1; node 2 has one child → 1 + 1 = 2; the root has one child → 1 + 2 = **3** ✅.

</details>

**13.** *(part 07)* Stairs again, but now you may climb **1, 2 or 3** steps at a time. Write the four lines.
How many ways are there to climb 5 steps?

<details>
<summary>Answer</summary>

```text
 EXPECTATION  ways(n) gives the number of ways to climb exactly n steps
 FAITH        ways(n - 1), ways(n - 2) and ways(n - 3) count the ways that start
              with a 1-step, a 2-step and a 3-step
 MY WORK      add the three answers
 BASE CASE    n = 0: 1;  n < 0: 0
```

Filling a table from the bottom: 1, 1, 2, 4, 7, **13** for n = 0 to 5.

</details>

**14.** *(part 07)* A robot moves only right or down in a 3 × 3 grid, but the **middle** cell is blocked. How
many paths lead from the top-left cell to the bottom-right cell? Write the four lines.

<details>
<summary>Answer</summary>

```text
 EXPECTATION  paths(r, c) gives the number of paths from cell (r, c)
              to the bottom-right cell
 FAITH        paths(r, c + 1) and paths(r + 1, c) count the paths that start
              by moving right and by moving down
 MY WORK      add the two answers
 BASE CASE    the bottom-right cell: 1;  outside the grid or the blocked cell: 0
```

**2** paths: along the top row and then down the right column, or down the left column and then along the
bottom row. (Without the block there would be 6.)

</details>

**15.** *(part 02)* Is a binary tree a **binary search tree** — is every value on a node's left side smaller
than the node, and every value on its right side bigger? Why is "check each node against its two children"
not enough? Use this tree:

```text
           4 (left child of 8)
                    ▲
                    │
                    │
      3             8
      ▲             ▲
      └──────┬──────┘
             │
       5 (the root)
```

<details>
<summary>Answer</summary>

Every node looks fine next to its own children: 3 < 5 < 8, and 4 < 8. But 4 sits on the **right** side of 5
and is smaller than 5 ❌. A node needs to know the range allowed by **everything above it** — so tell the
friends more:

```text
 EXPECTATION  valid(node, low, high) says yes if the tree at node is a search tree
              and all its values lie between low and high
 FAITH        valid(left child, low, node's value) and
              valid(right child, node's value, high) check each side
 MY WORK      yes only if low < node's value < high AND both friends say yes
 BASE CASE    no node: yes
```

The top call allows everything: valid(root, −∞, +∞). Here, 8 gets the range (5, +∞), so its left child 4
gets (5, 8) — and 4 is not bigger than 5 → **no** ✅.

</details>

**16.** *(part 03)* Remove every node with the value x from a linked list, and give back the new first node.
Check it on 7 → 3 → 7 → 1 with x = 7.

<details>
<summary>Answer</summary>

```text
 EXPECTATION  remove(head) gives the first node of the list that starts at head,
              with every x removed
 FAITH        remove(head's next) cleans the rest and gives back its first node
 MY WORK      if head holds x: give back the friend's list (head is dropped);
              otherwise: point head at the friend's list and give back head
 BASE CASE    an empty list: give back the empty list
```

Check: the friend cleans 3 → 7 → 1 into 3 → 1 (trust it). The head holds 7, so it is dropped: **3 → 1** ✅.

</details>

**17.** *(part 06)* Adding up 16 elements: (a) with "the rest of an array" (the friend gets i + 1), and
(b) by halves. For each, how many boxes (calls) are there, and how tall does the pile get?

<details>
<summary>Answer</summary>

- **(a)** i = 0, 1, …, 16: **17 boxes**, and the pile is **17** plates tall — a straight chain.
- **(b)** 16 + 8 + 4 + 2 + 1 = **31 boxes**, but only **5** levels (parts of 16, 8, 4, 2 and 1 elements).

Halves make more boxes but a much shorter pile.

</details>

### The hard ones

**18.** *(part 08)* How many subsets does {a, b, c, d} have? If you always try "skip" before "take", which
subset is printed **6th**?

<details>
<summary>Answer</summary>

2⁴ = **16** subsets. The order starts: { }, {d}, {c}, {c, d}, {b}, **{b, d}** — the 6th. (It works like
counting in binary with take = 1: the 6th is number 5 = 0101 → skip a, take b, skip c, take d.)

</details>

**19.** *(part 08)* How many orders of "abcd" are there? Trying the letters in order, which one is printed
right after "adcb"? What is on the pile at the moment "adcb" is printed?

<details>
<summary>Answer</summary>

4! = **24** orders. Right after "adcb" comes **"bacd"**: everything that starts with "a" is finished, so the
root un-chooses a and chooses b. The pile when "adcb" is printed is the path from the root:
perm(""), perm("a"), perm("ad"), perm("adc"), perm("adcb") — **5** plates.

</details>

**20.** *(part 08)* Combination sum: using 2, 3 and 5 as often as you like, print every group that adds up
to 8. Write the four lines.

<details>
<summary>Answer</summary>

```text
 EXPECTATION  combo(start, left, path) prints every group with sum left that uses
              numbers from position start on (each as often as you like), after path
 FAITH        for each position k >= start with a number <= left:
              combo(k, left - that number, path + that number) prints every
              group that continues with that number
 MY WORK      for each such k: choose, trust, un-choose
 BASE CASE    left = 0: print path
```

Printed: **[2, 2, 2, 2], [2, 3, 3], [3, 5]** — 3 groups.

</details>

**21.** *(part 08)* A student's subsets plan (skip before take) writes item i in the notebook for the "take"
friend, but **forgets to erase it** afterwards. What is printed for the items a, b? What should be printed?

<details>
<summary>Answer</summary>

Printed, as written in the notebook: **{ }, {b}, {b, a}, {b, a, b}**. It should be { }, {b}, {a}, {a, b}.

After the "take b" friend, b stays in the notebook. So when the root takes a, the notebook already says
"b" — every answer after that is dirty. The promise "give the notebook back as you got it" was broken, and
nobody after that can trust their notebook.

</details>

**22.** *(part 08)* 4-Queens by hand. The first queen in column 0 leads nowhere
([part 08](08-backtracking.md#6-pruning-faith-only-for-paths-that-can-still-work)). Now the search puts the
first queen in **column 1**. Continue row by row, always taking the first safe column. What is the first
solution?

<details>
<summary>Answer</summary>

- Row 1: columns 0, 1 and 2 are attacked by the queen in column 1 → column **3**.
- Row 2: column 1 and column 3 are taken, and column 2 is on a diagonal → column **0**.
- Row 3: columns 0, 1 and 3 are attacked → column **2**.

```text
  . Q . .
  . . . Q
  Q . . .
  . . Q .
```

The first solution: columns **1, 3, 0, 2**, found without a single dead end.

</details>

**23.** *(part 08)* Print every path from the root to a leaf, written like "7 -> 3 -> 1", for this tree:

```text
      1         6         9
      ▲         ▲         ▲
      │         └────┬────┘
      │              │
      3              8
      ▲              ▲
      └──────┬───────┘
             │
       7 (the root)
```

<details>
<summary>Answer</summary>

```text
 EXPECTATION  paths(node) prints, for every leaf in the tree at node, the notebook
              followed by the values on the path from node to that leaf -- and
              gives the notebook back as it got it
 FAITH        paths(each child) prints every path that continues through that child
 MY WORK      write node's value in the notebook (choose); if node is a leaf, print
              the notebook, otherwise trust both friends; erase the value (un-choose)
 BASE CASE    no node: print nothing
```

Printed: **7 -> 3 -> 1**, **7 -> 8 -> 6**, **7 -> 8 -> 9**.

</details>

**24.** *(part 07)* Tower of Hanoi with **6** disks, using faith only: (a) How many moves? (b) Which disk
moves on move 32? (c) Which disk moves on move 16?

<details>
<summary>Answer</summary>

- **(a)** Moving 5 disks takes 31 moves (trust it), so 31 + 1 + 31 = **63**.
- **(b)** Moves 1–31 are the first friend's job, so move 32 is my own move: **disk 6**.
- **(c)** Move 16 is inside the first friend's job (moving 5 disks). For **that** friend, moves 1–15 belong to
  its own first friend (4 disks), so move 16 is its own move: **disk 5**.

</details>

**25.** *(part 07)* A message of digits was made from letters with A = 1, B = 2, …, Z = 26. In how many ways
can "1226" be read? Write the four lines. (Careful: a 0 can't start a letter, so "06" can't be read at all.)

<details>
<summary>Answer</summary>

```text
 EXPECTATION  ways(i) gives the number of ways to read the digits from position i
              to the end
 FAITH        ways(i + 1) counts the ways whose first letter uses one digit;
              ways(i + 2) counts the ways whose first letter uses two digits
 MY WORK      add ways(i + 1) if digit i is not 0;
              add ways(i + 2) if digits i and i + 1 make a number from 10 to 26
 BASE CASE    i = length (nothing left to read): 1
```

**5** ways: 1 2 2 6 (ABBF), 1 2 26 (ABZ), 1 22 6 (AVF), 12 2 6 (LBF) and 12 26 (LZ).

</details>

---

## 3. Your score

| Your score | What it means | What to do next |
|---|---|---|
| 45 – 50 | rock solid 💪 | start the real problems in the [4-week plan](README.md#5-a-4-week-plan) |
| 38 – 44 | strong | redo the exercises of the parts behind the questions you missed |
| 30 – 37 | almost there | reread those parts, redo their exercises, and take the test again in 3 days |
| below 30 | not yet — and that is fine | go back to parts 01–04, then 05–08, and take the test again in a week |

Which part to revisit for a question you missed:

| Questions | Part |
|---|---|
| 1 | [01 · The Big Idea](01-the-big-idea.md) |
| 2, 15 | [02 · Expectation](02-expectation.md) |
| 3, 16 | [03 · Faith](03-faith.md) |
| 4, 5 | [04 · My Work and the Base Case](04-my-work-and-base-case.md) |
| 6, 7 | [05 · The Call Stack](05-the-call-stack.md) |
| 8, 9, 17 | [06 · The Recursion Tree](06-the-recursion-tree.md) |
| 10 – 14, 24, 25 | [07 · Recursion Patterns](07-patterns.md) |
| 18 – 23 | [08 · Backtracking](08-backtracking.md) |

**One more time, later:** a week after you pass, take the test again without looking at your old answers.
If you still score 40 or more, faith and expectation are yours for good. 🎉

---

⬅️ [08 · Backtracking](08-backtracking.md) · 🏠 [Guide home](README.md)
