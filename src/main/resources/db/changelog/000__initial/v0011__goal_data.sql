INSERT INTO public.goal
  (id, exercise_id, unit, comparing_unit, winning, description, validity_from, validity_to)
VALUES
  -- bench press
  ('f6074cf2-1c06-405a-beb0-9af10f72d0b4', 'f4f4dae4-7394-484b-9777-48f37d61d742', 'REPETITIONS', 'KILOGRAMS', 'MORE', 'Versuche dein Maximalgewicht beim Bankdrücken für z.B. eine, fünf oder acht Wiederholungen zu steigern.', '2019-01-01 00:00:00', '2999-12-31 23:59:59'),
  -- row fastest
  ('2e7cb209-07d0-4f5d-b7f8-05052d92046c', 'af0d303a-17a6-4371-bcef-941582d9eb80', 'METERS', 'SECONDS', 'LESS', 'Setze dir ein Ziel (z.B. 500m) und versuche deine Bestzeit zu unterbieten', '2019-01-01 00:00:00', '2999-12-31 23:59:59'),
  -- row kcal
  ('8995f71b-1c01-42d3-b80f-6553966d04af', 'af0d303a-17a6-4371-bcef-941582d9eb80', 'SECONDS', 'CALORIES', 'MORE', 'Versuche in einem gegebenen Zeitfenster so viele kcal wie möglich zu verbrennen.', '2019-01-01 00:00:00', '2999-12-31 23:59:59'),
  -- back squat
  ('1d9cc13f-a32f-4989-8753-cc3b38daa00a', '2480aecc-5417-4ec4-bf26-0240f424cd9e', 'REPETITIONS', 'KILOGRAMS', 'MORE', 'Versuche dein Maximalgewicht beim Back Squat für z.B. eine, fünf oder acht Wiederholungen zu steigern.', '2019-01-01 00:00:00', '2999-12-31 23:59:59');
