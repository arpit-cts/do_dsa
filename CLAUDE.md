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
- [`dsa/recursion/think_recursion/`](dsa/recursion/think_recursion/) is a **thinking-only** guide (no Java, by the
  owner's choice) in 9 parts (`01-…` to `09-…`, with `README.md` as the index): faith and expectation in
  depth, the call stack, the recursion tree, the seven patterns, backtracking, how much practice is enough,
  and 105 no-code exercises ending in a 25-question final test. Keep it code-free; point new recursion
  problems to it. If an exercise is added or removed, update the counts in its README and in the part.
  (Folder names the owner picks, like `think_recursion` and `maths_for_dsa`, are kept exactly as given.)
- `maths_for_dsa/` (beside `dsa/`) is a start-to-end maths course for DSA and FAANG interviews — see the
  rules in [The maths course](#the-maths-course) below.
- `podcast/` (beside `dsa/`) holds full podcast notes, one folder per episode:
  `podcast/<code>-<guest>-<topic>/` (lowercase, hyphens), e.g. `podcast/fo559-sahar-yousef-focus-and-memory/`.
  Each `README.md` has the whole conversation as spoken (speaker names, timestamps that link to the video,
  the video's chapters as numbered `##` sections), important lines highlighted in yellow with `<mark>` plus a
  🟡 **Key points** box after each chapter, my research notes in blue `> [!NOTE]` boxes with sources, cartoons
  in `images/` (SVG) and Mermaid diagrams. List every episode in [`podcast/README.md`](podcast/README.md).

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
   - Draw the **complete tree diagram growing from bottom to top, like a real tree** (never top to bottom):
     - the full recursion tree for a small input, including every base-case call;
     - the **root (the first call) in the bottom row**, each call's helpers just above it, and the **leaves
       (the base cases) in the top row**; every arrow points **up**, from a call to the helpers it asks;
     - only reversing the arrows of a tree that still has the first call at the top is **not** enough —
       the drawing itself must put the first call at the bottom;
     - explain both directions: the calls climb **up** to the leaves, the answers come back **down** to the root;
     - Mermaid: `flowchart BT` with `parent --> child` edges; define the root first and the left helper
       (Faith 1) before the right one, so the left-to-right order stays correct (GitHub renders Mermaid);
     - a plain-text ASCII version that is the **same upward-growing tree** (root at the bottom, `└─┬─┘`
       joints, `▲` arrows), not a sideways folder-style `├──` tree; keep lines ≤ about 93 characters;
     - a bottom-to-top "ladder of faith" (smallest input → biggest input), with a note that it is not the tree;
     - keep every line inside a Mermaid box short (about 22 characters) — longer lines get cut off.
   - Also include: a dry-run table, a line-by-line code walkthrough, time & space complexity with the reason,
     common mistakes, "try it yourself" exercises, and a one-minute recap.
   - Add a linked contents list at the top; keep headings emoji-free so the GitHub anchor links work.
   - For problems that are not recursive, keep the same child-friendly style: intuition first, how to *think*
     of the idea, diagrams of the process, a dry run, and complexity.

## The maths course

`maths_for_dsa/` teaches all the maths needed for DSA and FAANG interviews, from small to big.
The roadmap and chapter list live in [`maths_for_dsa/README.md`](maths_for_dsa/README.md).

- Chapters are folders `maths_for_dsa/NN-topic-name/` (two-digit number, lowercase, hyphens), in learning order.
- Every chapter contains:
  - `README.md` — the lesson;
  - one Java file of runnable demos for the chapter's formulas and algorithms (PascalCase name, no `package`,
    a `main` that prints small examples, Java 11 compatible, runs with `java <File>.java`).
    Print only ASCII (write `sqrt`, `->`, `x`, `<=`): Windows consoles show other characters as `?`.
    Handle the edge cases the lesson talks about (0, negatives, `Integer.MIN_VALUE`, overflow).
    Chapter 17 (mixed practice) is exercises only and has no Java file.
- When a chapter is added, renamed or removed, update the roadmap (table, diagram, checklist, cheat sheet if
  needed) and the previous/next links of the neighbouring chapters.

### Chapter README layout

1. `# NN · Title`, then a one-line promise in a quote: `> After this chapter you can …`.
2. A navigation line: `⬅️ [NN-1 · Title](../<folder>/) · 🏠 [Roadmap](../README.md) · [NN+1 · Title](../<folder>/) ➡️`.
3. A linked contents list of the numbered `##` sections.
4. `## 1. Why this matters for DSA` — where the idea shows up in real problems and interviews.
5. Concept sections from small to big. In each: a story or everyday picture first, then the rule, then *why* the
   rule is true, a diagram, a worked example by hand, and how it looks in Java. Add a
   "🧠 How to think of it yourself" note wherever it helps build the habit of discovering the idea.
6. `## N. Java code` — link the Java file, show the key methods, and paste the **real** output of running it.
7. `## N. Common mistakes`.
8. `## N. Interview patterns` — a table: pattern → how to recognise it → LeetCode problems (number and name).
9. `## N. Exercises` — **Level 1 · Warm-up** (by hand), **Level 2 · Practice**, **Level 3 · Interview**.
   At least 20 exercises per chapter (chapter 17: at least 50), numbered `**1.**`, `**2.**`, … across levels.
   Every exercise has its answer and a short reason hidden in a `<details>` block so I try first.
   Compute every numeric answer (Java, jshell, or a script) before writing it.
10. `## N. One-minute recap` — short bullets.

### Writing rules for the maths course

- Explain like to a child: simple words, short sentences, everyday analogies (sharing candies, pizza slices,
  clocks, stairs, dominoes). Emojis are fine in moderation, never in headings.
- Always show *why* a rule works and how you could discover it yourself — never only the formula.
- Diagrams everywhere: Mermaid plus plain-text pictures (number lines, tables, grids, ASCII trees).
  - Never draw a diagram top to bottom (`TD`/`TB`). Trees and ladders grow **bottom to top** (`flowchart BT`,
    with the root, first call or smallest step at the bottom). Step-by-step processes go **left to right**
    (`flowchart LR`).
  - Keep each line inside a Mermaid box to about 22 characters (use `<br/>`), or it gets cut off.
  - Keep each line inside a code block to 90 characters or fewer, so GitHub shows it without a scrollbar.
- Write maths as plain text with Unicode symbols (√, ², ≤, ≥, ≈, ×, log₂, ⌊ ⌋), not LaTeX `$…$`.
  Inside code blocks prefer plain names (`log2`, `floor`, `sqrt`, `x1`) so columns stay aligned.
- Section headings (`##` and deeper) have no emojis or symbols such as √ & / → or quotes, so their anchor
  links are predictable. Numbered sections look like `## 3. Plain words`.
- In tables write a pipe as `\|`, even inside backticks, or the table breaks.
- Leave a blank line after `</summary>` and before `</details>`, so the Markdown inside renders.
- Link related chapters (`../NN-topic/`) and related `dsa/` lessons instead of repeating them.
