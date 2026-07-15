# Getting updates after you fork

You forked this repo to your own GitHub account so you can commit your own work. Throughout the
class, new material (later days' answer keys, instructions, and templates) will be added to the
original repo. This doc shows you how to pull those updates into your fork.

## One-time setup

After forking, add the original repo as a second remote called `upstream` (your fork is already
your `origin`):

```
git remote add upstream <original-repo-url>
```

Check it worked:

```
git remote -v
```

You should see both `origin` (your fork) and `upstream` (the class repo).

## Pulling new updates

Whenever new class material is added, run:

```
git fetch upstream
git merge upstream/main
```

This pulls the new commits from the class repo into your local branch. Then push the merge to
your own fork so it's backed up on GitHub:

```
git push origin main
```

## If you get a merge conflict

A conflict means you and the instructor both changed the same lines of the same file (for
example, if you started editing a Template file that later gets corrected upstream). Git will
mark the conflicting sections in the file with `<<<<<<<`, `=======`, and `>>>>>>>`. Open the
file, decide which version (or combination) to keep, delete the conflict markers, then:

```
git add <the file(s) you fixed>
git commit
git push origin main
```

If you're stuck, ask an instructor — resolving conflicts is a normal part of using git, not a
sign something went wrong.

## Avoiding conflicts

New-day material (answer keys, instructions, templates for later days) will generally live in
new files you haven't touched, so most updates should merge with no conflicts at all. Conflicts
are most likely if you've made edits inside a file that gets corrected later (e.g. a shared
Template or Instructions file) — that's fine, just resolve it as above.
