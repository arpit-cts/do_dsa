# CLAUDE.md

Instructions for AI coding assistants (Claude Code, GitHub Copilot CLI, …) working in this repository.
Follow them in **every session** — the owner should never have to repeat them.

## About this repo

- Personal repo for learning **Data Structures & Algorithms in Java**.
- Layout: `dsa/<topic>/<problem-name>/` — lowercase, hyphen-separated folder names.
  - Example: `dsa/recursion/tower-of-hanoi/`
- Every problem folder contains:
  - `<ProblemName>.java` — the solution (PascalCase class name, no `package` line, a `main` method that runs a small example).
  - `README.md` — the explanation. GitHub shows it automatically when the folder is opened.
- Reference example for format and depth: [`dsa/recursion/tower-of-hanoi/`](dsa/recursion/tower-of-hanoi/).

## Whenever I ask for a new problem or topic

Do **all** of the following, even if my message only says "do <problem>":

1. **Create the folder** `dsa/<topic>/<problem-name>/` (create the topic folder too if it is missing).
2. **Write the Java solution** `<ProblemName>.java`:
   - Simple, beginner-readable code with meaningful names.
   - Short comments that label the thinking: `EXPECTATION`, `FAITH`, `MY WORK`, `BASE CASE`.
   - Must run with `java <ProblemName>.java` (JDK 11+) and with `javac` + `java`.
   - Compile and run it before finishing; any output shown in the README must be the real output.
3. **Write `README.md` — explain from start to end, like explaining to a child:**
   - Simple words, short sentences, everyday analogies (friends helping, boss and helpers, dominoes, ladders,
     plates). Emojis are fine in moderation.
   - Start with the problem as a small story: rules, a picture, and tiny inputs solved by hand (n = 1, 2, 3)
     to discover the pattern.
   - Use **Expectation → Faith → Meeting the expectation (+ Base case)** for every recursion-based problem
     (recursion, backtracking, trees, divide & conquer, DP built from recursion):
     - **Expectation** — what the function promises to do, in one plain sentence that mentions every parameter.
     - **Faith** — trust that the same function already works for a smaller input; don't trace it.
     - **Meeting the expectation** — the small extra work that turns the faith's result into the full answer.
     - **Base case** — the smallest input, where the answer is obvious.
   - Explain **how the faith and expectation are developed** — the thinking process, not just the answer:
     - how to discover the smaller sub-problem (look at what is blocking you, or what is left over);
     - why the faith is safe (dominoes / mathematical induction);
     - a reusable checklist of questions and habits for building this way of thinking.
   - Draw the **complete tree diagram, from bottom to top**:
     - the full recursion tree for a small input, including every base-case call;
     - how calls go **down** to the base cases and how answers come back **up**, level by level, to the first call;
     - a bottom-to-top "ladder of faith" (smallest input → biggest input);
     - use Mermaid (`flowchart TD` / `flowchart BT`, which GitHub renders) **and** a plain-text ASCII version.
   - Also include: a dry-run table, a line-by-line code walkthrough, time & space complexity with the reason,
     common mistakes, "try it yourself" exercises, and a one-minute recap.
   - Add a linked contents list at the top; keep headings emoji-free so the GitHub anchor links work.
   - For problems that are not recursive, keep the same child-friendly style: intuition first, how to *think*
     of the idea, diagrams of the process, a dry run, and complexity.
