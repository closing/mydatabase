package designpatterns.builder;

public class AngelBuilder extends ActorBuilder {
	public void buildType() {
		actor.setType("Angel");
	}

	public void buildSex() {
		actor.setSex("Female");
	}

	public void buildFace() {
		actor.setFace("Beautiful");
	}

	public void buildCostume() {
		actor.setCostume("Longuette");
	}

	public void buildHairstyle() {
		actor.setHairstyle("Long hair");
	}
}
