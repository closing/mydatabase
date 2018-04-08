package designpatterns.client;

import designpatterns.builder.Actor;
import designpatterns.builder.ActorBuilder;
import designpatterns.builder.ActorController;
import designpatterns.builder.HeroBuilder;

public class BuilderTest {
	public static void main(String[] args) {

		ActorBuilder ab;
		ab = new HeroBuilder();

		ActorController ac = new ActorController();
		Actor actor;
		actor = ac.construct(ab);;

		String type = actor.getType();
		System.out.println("Type: " + type);
		System.out.println("Sex: " + actor.getSex());
		System.out.println("Face: " + actor.getFace());
		System.out.println("Costume: " + actor.getCostume());
		System.out.println("Hairstyle: " + actor.getHairstyle());
	}
}
