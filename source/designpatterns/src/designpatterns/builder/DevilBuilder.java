package designpatterns.builder;

public class DevilBuilder extends ActorBuilder {
	public void buildType() {
		actor.setType("Devil");
	}

	public void buildSex() {
		actor.setSex("Want");
	}

	public void buildFace() {
		actor.setFace("Ugly");
	}

	public void buildCostume() {
		actor.setCostume("Black");
	}

	public void buildHairstyle() {
		actor.setHairstyle("Bald");
	}
}
