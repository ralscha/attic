/**
 * Service methods related to Scenarios.
 */
Ext.define("Phoenix.service.ScenarioService", {
	requires: [ "Deft.promise.Chain" ],
	inject: [ "scenarioStore", "probabilityStore", "revenueImpactStore", "affectedItemStore" ],
	config: {
		scenarioStore: null,
		probabilityStore: null,
		revenueImpactStore: null,
		affectedItemStore: null
	},

	constructor: function(config) {
		if (config === null) {
			config = {};
		}
		this.initConfig(config);
		return this.callParent(arguments);
	},

	/**
	 * Loads initial reference data: probabilities, revenue impacts, and affected item lists.
	 * 
	 * @return {Deft.promise.Promise}
	 */
	loadInitialData: function() {
		return Deft.Chain.parallel([ this.loadProbabilities, this.loadRevenueImpacts, this.loadAffectedItems ], this);
	},

	/**
	 * Loads reference set of {Phoenix.model.Probability} models.
	 * 
	 * @return {Deft.promise.Promise}
	 */
	loadProbabilities: function() {
		var deferred;
		deferred = Ext.create("Deft.promise.Deferred");
		this.getProbabilityStore().load({
			callback: function(records, operation, success) {
				if (success) {
					return deferred.resolve();
				}

				return deferred.reject("Error loading Probabilities");
			},
			scope: this
		});
		return deferred.promise;
	},

	/**
	 * Loads reference set of {Phoenix.model.RevenueImpact} models.
	 * 
	 * @return {Deft.promise.Promise}
	 */
	loadRevenueImpacts: function() {
		var deferred;
		deferred = Ext.create("Deft.promise.Deferred");
		this.getRevenueImpactStore().load({
			callback: function(records, operation, success) {
				if (success) {
					return deferred.resolve();
				}

				return deferred.reject("Error loading Revenue Impacts");
			},
			scope: this
		});
		return deferred.promise;
	},

	/**
	 * Loads reference set of {Phoenix.model.AffectedItem} models.
	 * 
	 * @return {Deft.promise.Promise}
	 */
	loadAffectedItems: function() {
		var deferred;
		deferred = Ext.create("Deft.promise.Deferred");
		this.getAffectedItemStore().load({
			callback: function(records, operation, success) {
				if (success) {
					return deferred.resolve();
				}

				return deferred.reject("Error loading Affected Items");
			},
			scope: this
		});
		return deferred.promise;
	},

	/**
	 * Loads the set of {Phoenix.model.Scenario} models.
	 * 
	 * @return {Deft.promise.Promise}
	 */
	loadScenarios: function() {
		var deferred;
		deferred = Ext.create("Deft.promise.Deferred");
		this.getScenarioStore().load({
			callback: function(records, operation, success) {
				if (success) {
					return deferred.resolve();
				}

				return deferred.reject("Error loading Scenarios");
			},
			scope: this
		});
		return deferred.promise;
	},

	/**
	 * Saves the passed {Phoenix.model.Scenario} and its associated {Phoenix.model.ScenarioItem} models.
	 * 
	 * @param {Phoenix.model.Scenario}
	 *            Scenario to save.
	 * @return {Deft.promise.Promise}
	 */
	saveScenario: function(scenario) {
		var sequence;
		if (this.isNewScenario(scenario)) {
			this.getScenarioStore().add(scenario);
		}
		scenario.set("dateUpdated", new Date());
		scenario.simulateServerCostBenefitAnalysis();

		sequence = [ this.syncScenarioStore ];

		return Deft.Chain.sequence(sequence, this);
	},

	/**
	 * Deletes the passed {Phoenix.model.Scenario} and its associated {Phoenix.model.ScenarioItem} models.
	 * 
	 * @param {Phoenix.model.Scenario}
	 *            Scenario to delete.
	 * @return {Deft.promise.Promise}
	 */
	deleteScenario: function(scenario) {
		this.getScenarioStore().remove(scenario);
		return Deft.Chain.sequence([ this.syncScenarioStore ], this);
	},

	/**
	 * Syncs the {Phoenix.store.ScenarioStore}.
	 * 
	 * @return {Deft.promise.Promise}
	 */
	syncScenarioStore: function() {
		var deferred;
		deferred = Ext.create("Deft.promise.Deferred");
		this.getScenarioStore().sync({
			success: function(batch, options) {
				return deferred.resolve();
			},
			failure: function(batch, options) {
				this.getScenarioStore().rejectChanges();
				return deferred.reject();
			},
			scope: this
		});
		return deferred.promise;
	},

	/**
	 * Returns true if the {Phoenix.model.Scenario} is not present in the {Phoenix.store.ScenarioStore}.
	 * 
	 * @param {Phoenix.model.Scenario}
	 * @return {Boolean}
	 */
	isNewScenario: function(scenario) {
		return !(this.getScenarioStore().getById(scenario.getId()) !== null);
	}

});
