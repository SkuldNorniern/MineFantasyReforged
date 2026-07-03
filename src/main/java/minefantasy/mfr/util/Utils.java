package minefantasy.mfr.util;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Sets;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Utils {

	public Utils() {
		throw new IllegalStateException("Util class cannot be instantiated");
	}

	public static <T> T nullValue() {
		return null;
	}

	public static boolean doesMatch(ItemStack item1, ItemStack item2) {
		return item2.getItem() == item1.getItem();
	}

	public static String convertSnakeCaseToSplitCapitalized(String string) {
		return WordUtils.capitalize(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, string).replaceAll("_", " "));
	}

	public static String convertSplitCapitalizedToSnakeCase(String string) {
		return string.toLowerCase().replaceAll(" ", "_");
	}

	public static String serializeList(Set<String> list) {
		return list.toString().replaceAll("[\\[|\\]]", "");
	}

	public static Set<String> deserializeList(String string) {
		List<String> list = Arrays.asList(StringUtils.splitByWholeSeparator(string, ","));
		list.replaceAll(String::trim);
		return Sets.newHashSet(list);
	}

	public static <T> Collection<T> emptyIfNull(List<T> list) {
		return list != null ? list : new ArrayList<>();
	}

	/** Replacement for the old IItemPropertyGetter functional interface. */
	@FunctionalInterface
	public interface IItemPropertyGetterFix {
		float applyPropertyGetter(ItemStack stack, @Nullable Level worldIn, @Nullable LivingEntity entityIn);
	}

	public static float percentDifferenceCalculator(int a, int b) {
		float absoluteDifference = Math.abs(a - b);
		float average = (a + b) / 2F;
		return 100 * (absoluteDifference / average);
	}

	public static Long gcd(List<Long> input) {
		long result = input.get(0);
		for (int i = 1; i < input.size(); i++) result = gcd(result, input.get(i));
		return result;
	}

	private static long gcd(Long a, Long b) {
		while (b > 0) {
			long temp = b;
			b = a % b;
			a = temp;
		}
		return a;
	}

	public static <T> int findSmallestListSize(Stream<List<T>> lists) {
		return lists.min(Comparator.comparingInt(List::size)).orElse(new ArrayList<>()).size();
	}

	public static boolean isInteger(String str) {
		if (str == null || str.isEmpty()) return false;
		int i = 0;
		if (str.charAt(0) == '-') {
			if (str.length() == 1) return false;
			i = 1;
		}
		for (; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c < '0' || c > '9') return false;
		}
		return true;
	}
}
