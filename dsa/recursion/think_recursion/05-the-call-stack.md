# 05 · The Call Stack

> After this part you can say exactly what the computer does when a function calls a friend: the pile of
> plates, the way in and the way back, and how much memory recursion uses.

⬅️ [04 · My Work and the Base Case](04-my-work-and-base-case.md) · 🏠 [Guide home](README.md) · [06 · The Recursion Tree](06-the-recursion-tree.md) ➡️

## Contents

1. [Deep down: the call stack](#1-deep-down-the-call-stack)
2. [Exercises](#2-exercises)
3. [One-minute recap](#3-one-minute-recap)

---

## 1. Deep down: the call stack

So how does the computer actually do it? When a function asks a friend, the computer must **not forget**
where it was. So it puts a **plate** on a pile. 🍽️ The plate remembers: *"I am sum(3), I'm waiting for my
friend, and when the answer comes I must add 3."* The friend gets a **new plate on top**. When a friend
finishes, its plate is taken off and its answer goes to the plate below.

The pile of plates is called the **call stack**. Here is sum(3), moment by moment (time goes left to right;
the first call is the bottom plate):

```text
                                [sum(0)]
                      [sum(1)]  [sum(1)]  [sum(1)]
            [sum(2)]  [sum(2)]  [sum(2)]  [sum(2)]  [sum(2)]
  [sum(3)]  [sum(3)]  [sum(3)]  [sum(3)]  [sum(3)]  [sum(3)]  [sum(3)]
  ==============================================================================
    1         2         3         4         5         6         7         8
  call 3    call 2    call 1    call 0    0 back    1 back    3 back    6 back
```

| Moment | What happens | The pile, bottom → top |
|---|---|---|
| 1 | sum(3) starts; it needs sum(2) | sum(3) |
| 2 | sum(2) starts; it needs sum(1) | sum(3), sum(2) |
| 3 | sum(1) starts; it needs sum(0) | sum(3), sum(2), sum(1) |
| 4 | sum(0) is the base case: it answers 0 at once | sum(3), sum(2), sum(1), sum(0) |
| 5 | sum(0)'s plate is removed; sum(1) gets 0 and gives back 0 + 1 = 1 | sum(3), sum(2), sum(1) |
| 6 | sum(1)'s plate is removed; sum(2) gets 1 and gives back 1 + 2 = 3 | sum(3), sum(2) |
| 7 | sum(2)'s plate is removed; sum(3) gets 3 and gives back 3 + 3 = 6 | sum(3) |
| 8 | sum(3)'s plate is removed: the answer 6 goes to whoever asked | (empty) |

Things to notice 👀

- **Every plate has its own n.** The plate of sum(3) and the plate of sum(2) never mix up their numbers.
  That is why the same function can be "running" four times at once.
- **The way in and the way back.** While plates are being added (moments 1–4) we are on the **way in**:
  work *before* the call happens here. While plates are removed (moments 5–8) we are on the **way back**:
  work *after* the call happens here. That's why "say n before the friend" counts **down** and "say n after
  the friend" counts **up** ([part 04](04-my-work-and-base-case.md)).
- **The pile height is the memory used.** sum(n) needs n + 1 plates. That is the **space complexity** of
  recursion ([Maths 07](../../../maths_for_dsa/07-recurrences/)). Java's pile is limited — usually a few
  thousand to tens of thousands of plates, depending on the settings and on how much each plate holds — and
  a pile that grows forever ends in a *StackOverflowError*.

---

## 2. Exercises

**No code.** Write the four lines — EXPECTATION, FAITH, MY WORK, BASE CASE — or do what the question asks,
in your own words, before you open the answer. Compare the **ideas**, not the exact words.

**1.** Predict, without drawing the whole tree: hello(n) says "hi n", asks the friend hello(n − 1), then says
"bye n"; hello(0) is silent. What does hello(3) say? Draw the pile of plates at the moment hello(0) runs.

<details>
<summary>Answer</summary>

**hi 3, hi 2, hi 1, bye 1, bye 2, bye 3.** The "hi"s happen on the way **in**, the "bye"s on the way
**back** — the top plate finishes first, so the byes come in reverse.

```text
 +------------+
 |  hello(0)  |  <- running: silent, it returns at once
 |  hello(1)  |  said "hi 1", will say "bye 1"
 |  hello(2)  |  said "hi 2", will say "bye 2"
 |  hello(3)  |  said "hi 3", will say "bye 3"     <- the first call
 +------------+
```

</details>

**2.** Draw the pile of plates for fact(4) at the moment fact(0) runs, and write next to each plate what it is
waiting to do. How many plates does fact(n) need at the tallest moment?

<details>
<summary>Answer</summary>

```text
 +-----------+
 |  fact(0)  |  <- running: the base case, it answers 1 at once
 |  fact(1)  |  waiting; then it gives back 1 x (its friend's answer)
 |  fact(2)  |  waiting; then it gives back 2 x (its friend's answer)
 |  fact(3)  |  waiting; then it gives back 3 x (its friend's answer)
 |  fact(4)  |  waiting; then it gives back 4 x (its friend's answer)   <- the first call
 +-----------+
```

5 plates. fact(n) needs **n + 1** plates at the tallest moment. Then the plates come off one by one, giving
back 1, 1, 2, 6 and finally 24.

</details>

**3.** countUp(3) says 1, 2, 3 ([part 04](04-my-work-and-base-case.md)). At the moment it says **2**, which
plates are on the pile? Which plates are already gone?

<details>
<summary>Answer</summary>

```text
 +--------------+
 |  countUp(2)  |  <- running: its friend has finished, so now it says "2"
 |  countUp(3)  |  waiting: it will say "3" when countUp(2) is gone   <- the first call
 +--------------+
   already gone: countUp(0) (it was silent) and countUp(1) (it said "1")
```

"2" is said on the **way back**: the plates above countUp(2) have already come off.

</details>

**4.** wave(n) says n, asks the friend wave(n − 2), then says n **again**; for n ≤ 0, wave(n) is silent.
What does wave(5) say? How tall does the pile get? What would happen if the base case were only
"n = 0: silent"?

<details>
<summary>Answer</summary>

**5, 3, 1, 1, 3, 5.** Each plate says its first number on the way in and its second on the way back. The
tallest pile has **4** plates: wave(5), wave(3), wave(1) and wave(−1) on top (silent).

With only "n = 0: silent", wave(1) asks wave(−1), which asks wave(−3), and so on — the odd numbers **jump
over** 0. The pile grows until the program crashes (exercise 6 below). "n ≤ 0" catches every chain.

</details>

**5.** How tall does the pile of plates get? (a) sum(1000); (b) fast power(a, 1000), where the friend gets
n / 2; (c) binary search in a sorted array of 1,000,000 elements; (d) the biggest value by halves in an array
of 8 elements.

<details>
<summary>Answer</summary>

- **(a)** **1,001** plates: sum(1000), sum(999), …, sum(0).
- **(b)** **11** plates: 1000, 500, 250, 125, 62, 31, 15, 7, 3, 1, 0.
- **(c)** About **21** plates: every friend gets half of the part, and 1,000,000 can only be halved about 20
  times before nothing is left.
- **(d)** **4** plates: [0..7], [0..3], [0..1], [0..0]. The tree has 15 boxes, but only **one path** is on the
  pile at a time ([part 06](06-the-recursion-tree.md#2-the-stack-and-the-tree-together)).

Halving is why fast power and binary search are so light on memory.

</details>

**6.** A student forgets the base case of countDown: "say n, then ask countDown(n − 1)". What happens to the
pile? Why does the program crash, instead of running forever like an endless loop?

<details>
<summary>Answer</summary>

The pile grows and grows: countDown(3), countDown(2), countDown(1), countDown(0), countDown(−1), … and it
says 3, 2, 1, 0, −1, −2, … Every plate takes a little memory, and the pile has a fixed size (for Java,
usually thousands to tens of thousands of plates). When it is full, Java stops the program with a
**StackOverflowError**.

An endless loop needs no new memory for each round, so it can spin forever. Endless recursion needs a **new
plate** every time — so it runs out of room.

</details>

**7.** "Every plate has its own n." A classmate says: "When countDown(2) asks countDown(1), n becomes 1 — so
after the friend is done, n is 1 inside countDown(2) too." Explain what is wrong, with a picture of the
plates.

<details>
<summary>Answer</summary>

Every call gets its **own** plate with its **own** n. The friend's n = 1 lives on a **new** plate on top;
the plate of countDown(2) still says n = 2, untouched:

```text
 +----------------+
 |  countDown(1)  |  n = 1   <- a new plate with its own n
 |  countDown(2)  |  n = 2   <- still 2: nobody changed it
 +----------------+
```

When the friend's plate comes off, countDown(2) sees its own n = 2 again. That is one more reason you can
trust a friend: it cannot mess up **your** inputs. Only things you share on purpose — like the notebook in
[part 08](08-backtracking.md#3-why-un-choose-the-shared-notebook) — can be changed by a friend.

</details>

**8.** Two ways to add 1 + 2 + … + n. (a) sum(n) = n + sum(n − 1): the adding happens **after** the friend
answers. (b) run(n, total) hands a running total **to** the friend: run(n, total) = run(n − 1, total + n),
and run(0, total) = total. Write the promise of run. Draw the plates of run(3, 0) at the moment the base case
runs. On which way — in or back — is the adding done?

<details>
<summary>Answer</summary>

```text
 EXPECTATION  run(n, total) gives total + 1 + 2 + ... + n
 FAITH        run(n - 1, total + n) gives (total + n) + 1 + 2 + ... + (n - 1)
 MY WORK      nothing more: pass the friend's answer on
 BASE CASE    n = 0: total
```

```text
 +---------------------+
 |  run(0, total = 6)  |  <- running: the answer 6 is already known
 |  run(1, total = 5)  |  waiting; it will pass 6 on
 |  run(2, total = 3)  |  waiting; it will pass 6 on
 |  run(3, total = 0)  |  waiting; it will pass 6 on   <- the first call
 +---------------------+
```

In (a) the adding happens on the **way back** — each plate adds its n after its friend answers. In (b) it
happens on the **way in** — the total grows as the plates pile up, and the way back only passes 6 down.
The extra input "total" carries the work done so far: the flexible friend again.

</details>

---

## 3. One-minute recap

- Every call is a **plate** on the call stack, with its **own** inputs. A friend's plate goes **on top**.
- Plates pile up on the **way in** and come off on the **way back** — work before the friend happens on the
  way in, work after it on the way back.
- The tallest pile is the **memory** recursion uses; a pile that never stops ends in a *StackOverflowError*.

---

⬅️ [04 · My Work and the Base Case](04-my-work-and-base-case.md) · 🏠 [Guide home](README.md) · [06 · The Recursion Tree](06-the-recursion-tree.md) ➡️
