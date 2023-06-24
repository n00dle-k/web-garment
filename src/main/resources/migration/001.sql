-- Forward Migration from version 1 to version 2
-- Migration scrips sets default values for grament data structure changes
-- Setting default values for garmnets
update garments set garment_type = 'BOTTOM', is_in_laundry = false;
-- Setting default values for events
update events set is_top_layer = false;

