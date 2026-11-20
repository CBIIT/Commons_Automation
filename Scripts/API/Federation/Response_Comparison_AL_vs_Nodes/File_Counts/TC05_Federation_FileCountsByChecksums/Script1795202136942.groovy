import utilities.API_Functions

// NOTE: Run this test manually. It is not in API-Federation_Regression because it is very heavy.
// /file/by/checksums/count returns one entry per distinct checksum (about 1.16 million values,
// roughly 61 MB of JSON from KidsFirst alone), and the response bodies are printed and logged
// several times per run, which can slow Katalon Studio a lot. Prefer console mode.

API_Functions.verifyFederationNodesVersusAggLayerCounts(
	'Files-Checksums',
	'AL_FileCounts_by_Checksums'
)